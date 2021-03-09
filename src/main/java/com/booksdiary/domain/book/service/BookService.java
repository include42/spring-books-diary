package com.booksdiary.domain.book.service;

import com.booksdiary.domain.book.domain.Book;
import com.booksdiary.domain.book.dto.BookCreateRequestServiceDto;
import com.booksdiary.domain.book.dto.BookResponseServiceDto;
import com.booksdiary.domain.book.exception.BookNotFoundException;
import com.booksdiary.domain.book.repository.BookRepository;
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

    @Transactional
    public BookResponseServiceDto createBook(BookCreateRequestServiceDto request) {
        final Book book = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .content(request.getContent())
                .publishDate(request.getPublishDate())
                .publisher(request.getPublisher())
                .author(request.getAuthor())
                .translator(request.getTranslator())
                .price(request.getPrice())
                .thumbnail(request.getThumbnail())
                .build();

        final Book savedBook = bookRepository.save(book);

        return new BookResponseServiceDto(savedBook);
    }


}
