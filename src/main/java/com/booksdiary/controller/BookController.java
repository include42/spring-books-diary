package com.booksdiary.controller;

import com.booksdiary.domain.BookCreateRequest;
import com.booksdiary.domain.BookResponse;
import com.booksdiary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @PostMapping("/api/books")
    public ResponseEntity<BookResponse> create(@RequestBody @Valid final BookCreateRequest request) {
        final BookResponse response = bookService.create(request);
        final URI uri = URI.create("/api/books/" + response.getId());

        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping("/api/books")
    public ResponseEntity<List<BookResponse>> list() {
        return ResponseEntity.ok()
                .body(bookService.list());
    }
}
