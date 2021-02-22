package com.booksdiary.controller;

import com.booksdiary.controller.dto.BookResponseDto;
import com.booksdiary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        final List<BookResponseDto> responses = bookService.getBooks()
                .stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable final Long bookId) {
        final BookResponseDto response = new BookResponseDto(bookService.getBook(bookId));

        return ResponseEntity.ok()
                .body(response);
    }
}
