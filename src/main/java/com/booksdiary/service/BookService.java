package com.booksdiary.service;

import com.booksdiary.domain.Book;
import com.booksdiary.domain.BookRepository;
import com.booksdiary.exception.BookNotFoundException;
import com.booksdiary.service.dto.BookResponseServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookResponseServiceDto> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponseServiceDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponseServiceDto getBook(Long bookId) {
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        return new BookResponseServiceDto(book);
    }
}
