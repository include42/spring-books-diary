package com.booksdiary.service.dto;

import com.booksdiary.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookResponseServiceDto {
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

    public BookResponseServiceDto(Book book) {
        this.id = book.getId();
        this.createAt = book.getCreatedAt();
        this.isbn = book.getIsbn();
        this.name = book.getName();
        this.content = book.getContent();
        this.publishDate = book.getPublishDate();
        this.publisher = book.getPublisher();
        this.author = book.getAuthor();
        this.translator = book.getTranslator();
        this.price = book.getPrice();
        this.thumbnail = book.getThumbnail();
        this.page = book.getPage();
    }
}
