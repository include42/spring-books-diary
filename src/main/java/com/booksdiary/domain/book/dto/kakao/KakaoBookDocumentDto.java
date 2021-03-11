package com.booksdiary.domain.book.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoBookDocumentDto {
    private String title;
    private String contents;
    private String url;
    private String isbn;
    private ZonedDateTime datetime;
    private List<String> authors;
    private String publisher;
    private List<String> translator;
    private Long price;
    private Long salePrice;
    private String thumbnail;
    private String status;
}
