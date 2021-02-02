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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // TODO: 2021/02/01 얘는 뭔데? 설명 추가
public class BookServiceTest {
    private static final Long 도서_ID_1 = 1L;
    private static final String 도서_이름_1 = "도서_이름_1";

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @DisplayName("Books 생성이 올바르게 수행된다.")
    @Test
    void createTest() {
        Book book = new Book(도서_ID_1, 도서_이름_1);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookCreateRequest request = new BookCreateRequest(도서_이름_1);

        BookResponse response = bookService.create(request);

        assertThat(response.getName()).isEqualTo(도서_이름_1);
    }
}
