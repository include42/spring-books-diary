package com.booksdiary.controller.dto;

import com.booksdiary.service.dto.BookResponseServiceDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookResponseDto {
    private final Long id;
    private final LocalDateTime createAt;
    private final String isbn;
    private final String name;
    private final String content;
    private final LocalDateTime publishDate;
    private final String publisher;
    private final String author;
    private final String translator;
    private final Long price;
    private final String thumbnail;
    private final Long page;

    public BookResponseDto(BookResponseServiceDto serviceDto) {
        this.id = serviceDto.getId();
        this.createAt = serviceDto.getCreateAt();
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
