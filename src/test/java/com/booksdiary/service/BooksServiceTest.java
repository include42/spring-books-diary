package com.booksdiary.service;

import com.booksdiary.domain.Books;
import com.booksdiary.domain.BooksCreateRequest;
import com.booksdiary.domain.BooksRepository;
import com.booksdiary.domain.BooksResponse;
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
public class BooksServiceTest {
    private static final Long 도서_ID_1 = 1L;
    private static final String 도서_이름_1 = "도서_이름_1";

    private BooksService booksService;

    @Mock
    private BooksRepository booksRepository;

    @BeforeEach
    void setUp() {
        booksService = new BooksService(booksRepository);
    }

    @DisplayName("Books 생성이 올바르게 수행된다.")
    @Test
    void createTest() {
        Books books = new Books(도서_ID_1, 도서_이름_1);
        when(booksRepository.save(any(Books.class))).thenReturn(books);
        BooksCreateRequest request = new BooksCreateRequest(도서_이름_1);

        BooksResponse response = booksService.create(request);

        assertThat(response.getName()).isEqualTo(도서_이름_1);
    }
}
