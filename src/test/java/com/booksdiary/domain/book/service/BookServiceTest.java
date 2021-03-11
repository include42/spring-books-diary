package com.booksdiary.domain.book.service;

import com.booksdiary.domain.book.domain.Book;
import com.booksdiary.domain.book.dto.BookCreateRequestServiceDto;
import com.booksdiary.domain.book.dto.BookResponseServiceDto;
import com.booksdiary.domain.book.exception.BookNotFoundException;
import com.booksdiary.domain.book.repository.BookRepository;
import com.booksdiary.utils.BookGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.booksdiary.utils.BookGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @DisplayName("Book 전체 목록 조회 요청 시 올바르게 수행된다.")
    @Test
    void getBooksTest() {
        List<Book> books = BookGenerator.createBooks();
        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponseServiceDto> foundBooks = bookService.getBooks();

        assertThat(foundBooks)
                .hasSize(2)
                .extracting("title")
                .containsOnly(도서_제목_1, 도서_제목_2);
    }

    @DisplayName("Book 개별 조회 요청 시 올바르게 수행된다.")
    @Test
    void getBookTest() {
        Book book = BookGenerator.createBook();
        when(bookRepository.findById(도서_ID_1)).thenReturn(Optional.of(book));

        BookResponseServiceDto foundBook = bookService.getBook(도서_ID_1);

        assertThat(foundBook.getId()).isEqualTo(도서_ID_1);
        assertThat(foundBook.getTitle()).isEqualTo(도서_제목_1);
        assertThat(foundBook.getIsbn()).isEqualTo(도서_ISBN);
    }

    @DisplayName("예외 테스트 : Book 개별 조회 요청 시 해당 도서가 없다면 예외가 발생한다.")
    @Test
    void getBookWitheExceptionTest() {
        when(bookRepository.findById(도서_ID_1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBook(도서_ID_1))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage(도서_ID_1 + "에 해당하는 도서를 찾을 수 없습니다.");
    }

    @DisplayName("Book 생성 요청 시 올바르게 수행된다.")
    @Test
    void createBookTest() {
        BookCreateRequestServiceDto request = BookGenerator.createBookCreateRequestServiceDto();
        Book book = BookGenerator.createBook();
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponseServiceDto createdBook = bookService.createBook(request);

        assertThat(createdBook.getId()).isEqualTo(도서_ID_1);
        assertThat(createdBook.getTitle()).isEqualTo(도서_제목_1);
        assertThat(createdBook.getIsbn()).isEqualTo(도서_ISBN);
    }
}
