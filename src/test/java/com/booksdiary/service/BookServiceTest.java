package com.booksdiary.service;

import com.booksdiary.controller.dto.BookResponseDto;
import com.booksdiary.domain.Book;
import com.booksdiary.domain.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("Book 전체 목록 조회 요청 시 올바르게 수행된다.")
    @Test
    void listTest() {
        List<Book> books = Arrays.asList(
                // TODO: 2021/02/17 테스트를 위한 팩토리 클래스 만들기, 카일의 글 참고(우테크코 블로그)
                new Book(도서_ID_1, 도서_이름_1),
                new Book(도서_ID_2, 도서_이름_2)
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponseDto> foundBooks = bookService.list();

        assertThat(foundBooks)
                .hasSize(2)
                .extracting("name")
                .containsOnly(도서_이름_1, 도서_이름_2);
    }
}
