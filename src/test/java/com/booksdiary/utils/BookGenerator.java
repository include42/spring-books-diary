package com.booksdiary.utils;

import com.booksdiary.domain.book.domain.Book;
import com.booksdiary.domain.book.dto.BookCreateRequestServiceDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BookGenerator {
    public static final Long 도서_ID_1 = 1L;
    public static final Long 도서_ID_2 = 2L;
    public static final LocalDateTime 도서_생성일 = LocalDateTime.now();
    public static final String 도서_ISBN = "978-3-16-148410-0";
    public static final String 도서_잘못된_ISBN = "123";
    public static final String 도서_제목_1 = "도서 이름 1번";
    public static final String 도서_제목_2 = "도서 이름 2번";
    public static final String 도서_내용 = "도서의 내용입니다.";
    public static final LocalDateTime 도서_출간일 = LocalDateTime.now();
    public static final String 도서_출판사 = "이 도서의 출판사 이름입니다.";
    public static final String 도서_작가 = "이 도서의 작가입니다.";
    public static final String 도서_번역가 = "이 도서의 번역가입니다.";
    public static final Long 도서_가격 = 10000L;
    public static final String 도서_썸네일 = "https://www.kafka.com/image/1.jpg";
    public static final String 도서_잘못된_썸네일 = "132.com";

    private static final Book book1, book2;
    private static final BookCreateRequestServiceDto bookCreateRequestServiceDto;

    static {
        book1 = Book.builder()
                .id(도서_ID_1)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .title(도서_제목_1)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .build();
        book2 = Book.builder()
                .id(도서_ID_2)
                .createdAt(도서_생성일)
                .isbn(도서_ISBN)
                .title(도서_제목_2)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .build();
        bookCreateRequestServiceDto = BookCreateRequestServiceDto.builder()
                .isbn(도서_ISBN)
                .title(도서_제목_2)
                .content(도서_내용)
                .publishDate(도서_출간일)
                .publisher(도서_출판사)
                .author(도서_작가)
                .translator(도서_번역가)
                .price(도서_가격)
                .thumbnail(도서_썸네일)
                .build();
    }

    public static Book createBook() {
        return book1;
    }

    public static List<Book> createBooks() {
        return Arrays.asList(book1, book2);
    }

    public static BookCreateRequestServiceDto createBookCreateRequestServiceDto() {
        return bookCreateRequestServiceDto;
    }
}
