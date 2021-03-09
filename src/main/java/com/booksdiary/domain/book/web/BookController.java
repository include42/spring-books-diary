package com.booksdiary.domain.book.web;

import com.booksdiary.domain.book.dto.BookCreateRequestDto;
import com.booksdiary.domain.book.dto.BookCreateRequestServiceDto;
import com.booksdiary.domain.book.dto.BookResponseDto;
import com.booksdiary.domain.book.service.BookClient;
import com.booksdiary.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;
    private final BookClient bookClient;

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

    @PostMapping("/api/books")
    public ResponseEntity<BookResponseDto> createBook(@RequestBody @Valid final BookCreateRequestDto request) {
        final BookCreateRequestServiceDto bookCreateRequest = bookClient.getBookDocuments(request.getIsbn());
        final BookResponseDto response = new BookResponseDto(bookService.createBook(bookCreateRequest));
        final URI uri = URI.create("/api/books/" + response.getId());

        return ResponseEntity.created(uri)
                .body(response);
    }
}
