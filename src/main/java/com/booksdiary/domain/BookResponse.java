package com.booksdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String name;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
    }
}
