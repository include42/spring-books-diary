package com.booksdiary.domain.book.exception;

import com.booksdiary.global.error.exception.EntityNotFoundException;

/**
 * BookNotFoundException은 Book을 검색, 조회할 때 결과를 찾지 못할 경우 호출된다.
 *
 * @author GeonHee Lee
 * @see EntityNotFoundException
 */
public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Long bookId) {
        super(bookId + "에 해당하는 도서를 찾을 수 없습니다.");
    }
}
