package com.booksdiary.controller;

import com.booksdiary.domain.BooksCreateRequest;
import com.booksdiary.domain.BooksResponse;
import com.booksdiary.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class BooksController {
    private final BooksService booksService;

    @PostMapping("/api/books")
    public ResponseEntity<BooksResponse> create(@RequestBody @Valid final BooksCreateRequest request) {
        final BooksResponse response = booksService.create(request);
        final URI uri = URI.create("/api/books/" + response.getId());

        return ResponseEntity.created(uri)
                .body(response);
    }
}
