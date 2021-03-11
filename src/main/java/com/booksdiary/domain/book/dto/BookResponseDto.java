package com.booksdiary.domain.book.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookResponseDto {
    private final Long id;
    private final LocalDateTime createAt;
    private final String isbn;
    private final String title;
    private final String content;
    private final LocalDateTime publishDate;
    private final String publisher;
    private final String author;
    private final String translator;
    private final Long price;
    private final String thumbnail;

    public BookResponseDto(BookResponseServiceDto serviceDto) {
        this.id = serviceDto.getId();
        this.createAt = serviceDto.getCreateAt();
        this.isbn = serviceDto.getIsbn();
        this.title = serviceDto.getTitle();
        this.content = serviceDto.getContent();
        this.publishDate = serviceDto.getPublishDate();
        this.publisher = serviceDto.getPublisher();
        this.author = serviceDto.getAuthor();
        this.translator = serviceDto.getTranslator();
        this.price = serviceDto.getPrice();
        this.thumbnail = serviceDto.getThumbnail();
    }
}
