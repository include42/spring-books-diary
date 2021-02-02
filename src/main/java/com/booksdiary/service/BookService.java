package com.booksdiary.service;

import com.booksdiary.domain.Book;
import com.booksdiary.domain.BookCreateRequest;
import com.booksdiary.domain.BookRepository;
import com.booksdiary.domain.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
