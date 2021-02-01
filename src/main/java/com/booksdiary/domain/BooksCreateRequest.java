package com.booksdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BooksCreateRequest {
    @NotBlank
    String name;

    public Books toBooks() {
        return Books.builder()
                .name(this.name)
                .build();
    }
}
