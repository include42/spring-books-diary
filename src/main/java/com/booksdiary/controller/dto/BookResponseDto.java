package com.booksdiary.controller.dto;

import com.booksdiary.service.dto.BookResponseServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookResponseDto {
    @NotBlank
    private final String isbn;

    @NotBlank
    private final String name;

    @NotBlank
    private final String content;

    @NotNull
    private final LocalDateTime publishDate;

    @NotBlank
    private final String publisher;

    @NotNull
    private final String author;

    private final String translator;

    @Positive
    private final Long price;

    @NotBlank
    private final String thumbnail;

    @Positive
    private final Long page;


    public BookResponseDto(BookResponseServiceDto serviceDto) {
        this.isbn = serviceDto.getIsbn();
        this.name = serviceDto.getName();
        this.content = serviceDto.getContent();
        this.publishDate = serviceDto.getPublishDate();
        this.publisher = serviceDto.getPublisher();
        this.author = serviceDto.getAuthor();
        this.translator = serviceDto.getTranslator();
        this.price = serviceDto.getPrice();
        this.thumbnail = serviceDto.getThumbnail();
        this.page = serviceDto.getPage();
    }
}
