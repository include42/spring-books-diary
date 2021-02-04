package com.booksdiary.service;

import com.booksdiary.domain.Book;
import com.booksdiary.domain.BookCreateRequest;
import com.booksdiary.domain.BookRepository;
import com.booksdiary.domain.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final Long 도서_ID_1 = 1L;
    private static final Long 도서_ID_2 = 2L;
    private static final String 도서_이름_1 = "도서_이름_1";
    private static final String 도서_이름_2 = "도서_이름_2";

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @DisplayName("Book 생성 요청 시 올바르게 수행된다.")
    @Test
    void createTest() {
        Book book = new Book(도서_ID_1, 도서_이름_1);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookCreateRequest request = new BookCreateRequest(도서_이름_1);

        BookResponse response = bookService.create(request);

        assertThat(response.getName()).isEqualTo(도서_이름_1);
    }

    @DisplayName("Book 전체 목록 조회 요청 시 올바르게 수행된다.")
    @Test
    void listTest() {
        List<Book> books = Arrays.asList(
                new Book(도서_ID_1, 도서_이름_1),
                new Book(도서_ID_2, 도서_이름_2)
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponse> foundBooks = bookService.list();

        assertThat(foundBooks)
                .hasSize(2)
                .extracting("name")
                .containsOnly(도서_이름_1, 도서_이름_2);
    }
}
