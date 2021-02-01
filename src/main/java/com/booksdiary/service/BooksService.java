package com.booksdiary.service;

import com.booksdiary.domain.Books;
import com.booksdiary.domain.BooksCreateRequest;
import com.booksdiary.domain.BooksRepository;
import com.booksdiary.domain.BooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Transactional
    public BooksResponse create(final BooksCreateRequest request) {
        final Books books = request.toBooks();
        final Books savedBooks = booksRepository.save(books);

        return new BooksResponse(savedBooks);
    }
}
