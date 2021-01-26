package com.booksdiary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class BookRepositoryTest {
    // TODO: 2021/01/26 변수명 한글로 하는 이유 기재
    private static final Long 도서_ID_1 = 1L;
    private static final String 도서_이름_1 = "테스트용 도서 이름 1번";

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Book의 생성을 요청할 때, 올바르게 수행된다.")
    @Test
    void createTest() {
        // TODO: 2021/01/26 id는 자동생성이니 빼준다. 일단 이름으로 테스트
        Book book = Book.builder().
                name(도서_이름_1).
                build();

        bookRepository.save(book);

        List<Book> savedBooks = bookRepository.findAll();

        assertThat(savedBooks)
                .hasSize(1)
                .extracting("name")
                .containsOnly(도서_이름_1);
    }
}
