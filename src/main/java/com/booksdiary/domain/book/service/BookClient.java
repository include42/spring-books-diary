package com.booksdiary.domain.book.service;

import com.booksdiary.domain.book.dto.BookCreateRequestServiceDto;
import com.booksdiary.domain.book.dto.kakao.KakaoBookDocumentDto;
import com.booksdiary.domain.book.dto.kakao.KakaoBookDto;
import com.booksdiary.domain.book.exception.ISBNNotFoundException;
import com.booksdiary.global.common.RestProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

// TODO: 2021/03/05 isbn으로 0000000000000 보내면, 테스트 데이터가 찍힘. 이때 NullPointerException 발생함. 예외처리 바람.
@Slf4j(topic = "HTTP_FILE_LOGGER")
@Service
public class BookClient {
    private final WebClient webClient;

    public BookClient(WebClient.Builder webClientBuilder, final RestProperties restProperties) {
        this.webClient = webClientBuilder
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", restProperties.getAuthorization())
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isInfoEnabled()) {
                log.info("url : {}", clientRequest.url());
                log.info("request : {}", clientRequest.method());
                clientRequest.headers()
                        .forEach((name, values) ->
                                values.forEach(value ->
                                        log.info("{} : {}", name, value)));
            }
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (log.isInfoEnabled()) {
                log.info("response : {}", clientResponse.statusCode());
                clientResponse.headers()
                        .asHttpHeaders()
                        .forEach((name, values) ->
                                values.forEach(value ->
                                        log.info("{} : {}", name, value)));
            }
            return Mono.just(clientResponse);
        });
    }

    public BookCreateRequestServiceDto getBookDocuments(final String isbn) {
        Mono<KakaoBookDto> kakaoBookMonos = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/search/book")
                        .queryParam("sort", "accuracy")
                        .queryParam("page", "1")
                        .queryParam("size", "10")
                        .queryParam("target", "isbn")
                        .queryParam("query", "0000000000001")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.just(new HttpClientErrorException(clientResponse.statusCode())))
                .bodyToMono(KakaoBookDto.class);

        KakaoBookDto kakaoBookDto = kakaoBookMonos.blockOptional()
                .orElseThrow(() -> new ISBNNotFoundException(isbn));

        validateKakaoBookDto(kakaoBookDto, isbn);
        KakaoBookDocumentDto kakaoBook = getKakaoBookDocumentDto(kakaoBookDto);

        return BookCreateRequestServiceDto.builder()
                .title(kakaoBook.getTitle())
                .contents(kakaoBook.getContents())
                .url(kakaoBook.getUrl())
                .isbn(isbn)
                .datetime(kakaoBook.getDatetime().toLocalDateTime())
                .authors(combineAuthors(kakaoBook.getAuthors()))
                .publisher(kakaoBook.getPublisher())
                .translator(combineTranslators(kakaoBook.getTranslator()))
                .price(kakaoBook.getPrice())
                .thumbnail(kakaoBook.getThumbnail())
                .build();
    }

    private void validateKakaoBookDto(KakaoBookDto kakaoBookDto, String isbn) {
        if (Objects.isNull(kakaoBookDto) || Objects.isNull(kakaoBookDto.getMeta())) {
            throw new ISBNNotFoundException(isbn);
        }
        List<KakaoBookDocumentDto> kakaoBookDocuments = kakaoBookDto.getDocuments();
        if (Objects.isNull(kakaoBookDocuments) || kakaoBookDocuments.isEmpty()) {
            throw new ISBNNotFoundException(isbn);
        }
    }

    private KakaoBookDocumentDto getKakaoBookDocumentDto(KakaoBookDto kakaoBookDto) {
        List<KakaoBookDocumentDto> kakaoBookDocuments = kakaoBookDto.getDocuments();

        return kakaoBookDocuments.get(0);
    }

    private String combineAuthors(List<String> authors) {
        return String.join(",", authors);
    }

    private String combineTranslators(List<String> translators) {
        if (Objects.isNull(translators) || translators.isEmpty()) {
            return Strings.EMPTY;
        }
        return String.join(",", translators);
    }
}
