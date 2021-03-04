package com.booksdiary.domain.book.exception;

import com.booksdiary.global.error.exception.InvalidValueException;

/**
 * ISBNInvalidException은 ISBN으로 Book을 외부 서버에 요청, 조회할 때 결과를 찾지 못할 경우 호출된다.
 *
 * @author GeonHee Lee
 * @see InvalidValueException
 */
public class ISBNNotFoundException extends InvalidValueException {
    public ISBNNotFoundException(String isbn) {
        super(isbn + "에 해당하는 도서 검색 결과를 찾을 수 없습니다.");
    }
}
