package com.booksdiary.domain.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class BookCreateRequestServiceDto {
    private final String title;
    private final String content;
    private final String url;
    private final String isbn;
    private final LocalDateTime publishDate;
    private final String author;
    private final String publisher;
    private final String translator;
    private final Long price;
    private final String thumbnail;
}
