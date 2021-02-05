package com.booksdiary.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId) {
        super(bookId + "에 해당하는 도서를 찾을 수 없습니다.");
    }
}
