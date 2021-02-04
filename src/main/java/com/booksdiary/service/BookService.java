package com.booksdiary.service;

import com.booksdiary.domain.Book;
import com.booksdiary.domain.BookCreateRequest;
import com.booksdiary.domain.BookRepository;
import com.booksdiary.domain.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public BookResponse create(final BookCreateRequest request) {
        final Book book = request.toBook();
        final Book savedBook = bookRepository.save(book);

        return new BookResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> list() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
}
