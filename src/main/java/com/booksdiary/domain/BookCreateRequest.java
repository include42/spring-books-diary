package com.booksdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {
    @NotBlank
    String name;

    public Book toBooks() {
        return Book.builder()
                .name(this.name)
                .build();
    }
}
