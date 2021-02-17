package com.booksdiary.controller;

import com.booksdiary.controller.dto.BookResponseDto;
import com.booksdiary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public ResponseEntity<List<BookResponseDto>> list() {
        return ResponseEntity.ok()
                .body(bookService.list());
    }
}
