package com.booksdiary.service;

import com.booksdiary.domain.Book;
import com.booksdiary.domain.BookRepository;
import com.booksdiary.service.dto.BookResponseServiceDto;
import com.booksdiary.utils.BookGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.booksdiary.utils.BookGenerator.도서_이름_1;
import static com.booksdiary.utils.BookGenerator.도서_이름_2;
import static org.assertj.core.api.Assertions.assertThat;
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
    void listTest() {
        List<Book> books = BookGenerator.createBooks();
        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponseServiceDto> foundBooks = bookService.list();

        assertThat(foundBooks)
                .hasSize(2)
                .extracting("name")
                .containsOnly(도서_이름_1, 도서_이름_2);
    }
}
