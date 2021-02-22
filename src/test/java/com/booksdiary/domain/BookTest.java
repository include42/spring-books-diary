package com.booksdiary.domain;

import com.booksdiary.utils.BookGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static com.booksdiary.utils.BookGenerator.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BookTest {
    private static final Logger logger = LoggerFactory.getLogger(BookTest.class);
    private static Validator validator;

    @BeforeAll
    public static void init() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("책을 생성하면, 검증을 통과한다.")
    @Test
    void createTest() {
        Book book = BookGenerator.createBook();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations.isEmpty()).isTrue();
    }


    @DisplayName("예외 테스트 : 잘못된 ISBN이 들어오면 예외가 발생한다.")
    @Test
    void invalidISBNTest() {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_잘못된_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("올바르지 않은 ISBN입니다");
    }

    @DisplayName("예외 테스트 : null인 ISBN이 들어오면 예외가 발생한다.")
    @NullSource
    @ParameterizedTest
    void nullISBNTest(String invalidISBN) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(invalidISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("널이어서는 안됩니다");
    }

    @DisplayName("예외 테스트 : 비어있거나 null인 이름이 들어오면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void blankNameTest(String invalidName) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(invalidName)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("공백일 수 없습니다");
    }

    @DisplayName("예외 테스트 : 비어있거나 null인 내용이 들어오면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void blankContentTest(String invalidContent) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(invalidContent)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("공백일 수 없습니다");
    }

    @DisplayName("예외 테스트 : null인 출판 날짜가 들어오면 예외가 발생한다.")
    @NullSource
    @ParameterizedTest
    void nullPublishDateTest(LocalDateTime invalidDate) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(invalidDate)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("널이어서는 안됩니다");
    }

    @DisplayName("예외 테스트 : 비어있거나 null인 출판사 정보가 들어오면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void blankPublisherTest(String invalidPublisher) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(invalidPublisher)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("공백일 수 없습니다");
    }

    @DisplayName("예외 테스트 : 비어있거나 null인 작가 정보가 들어오면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void blankAuthorTest(String invalidAuthor) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(invalidAuthor)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("공백일 수 없습니다");
    }

    @DisplayName("예외 테스트 : null인 도서 가격 정보가 들어오면 예외가 발생한다.")
    @NullSource
    @ParameterizedTest
    void nullPriceTest(Long invalidPrice) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(invalidPrice)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("널이어서는 안됩니다");
    }

    @DisplayName("예외 테스트 : 0보다 작거나 같은 페이지 정보가 들어오면 예외가 발생한다.")
    @ValueSource(longs = {-100, 0})
    @ParameterizedTest
    void notPositivePriceTest(Long invalidPrice) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(invalidPrice)
                .thumbnail(도서_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("0보다 커야 합니다");
    }

    @DisplayName("예외 테스트 : null인 썸네일 정보가 들어오면 예외가 발생한다.")
    @NullSource
    @ParameterizedTest
    void nullThumbnailTest(String invalidThumbnail) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(invalidThumbnail)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("널이어서는 안됩니다");
    }

    @DisplayName("예외 테스트 : 잘못된 썸네일 정보가 들어오면 예외가 발생한다.")
    @Test
    void invalidThumbnailTest() {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_잘못된_썸네일)
                .page(도서_페이지)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("올바른 URL이어야 합니다");
    }

    @DisplayName("예외 테스트 : null인 도서 페이지 정보가 들어오면 예외가 발생한다.")
    @NullSource
    @ParameterizedTest
    void nullPageTest(Long invalidPage) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(invalidPage)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("널이어서는 안됩니다");
    }

    @DisplayName("예외 테스트 : 0보다 작거나 같은 페이지 정보가 들어오면 예외가 발생한다.")
    @ValueSource(longs = {-100, 0})
    @ParameterizedTest
    void notPositivePageTest(Long invalidPage) {
        Book book = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .name(도서_이름_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .page(invalidPage)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        for (ConstraintViolation<Book> violation : violations) {
            logger.info(violation::getMessage);
        }
        assertThat(violations.isEmpty()).isFalse();
        assertThat(violations.toString()).contains("0보다 커야 합니다");
    }
}
