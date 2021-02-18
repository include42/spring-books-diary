package com.booksdiary.service.dto;

import com.booksdiary.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookResponseServiceDto {
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

    public BookResponseServiceDto(Book book) {
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
