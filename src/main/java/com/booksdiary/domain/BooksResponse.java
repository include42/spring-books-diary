package com.booksdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BooksResponse {
    private Long id;
    private String name;

    public BooksResponse(Books books) {
        this.id = books.getId();
        this.name = books.getName();
    }
}
