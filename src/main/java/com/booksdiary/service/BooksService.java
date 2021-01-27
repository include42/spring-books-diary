package com.booksdiary.service;

import com.booksdiary.domain.BooksCreateRequest;
import com.booksdiary.domain.BooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BooksService {

    @Transactional
    public BooksResponse create(final BooksCreateRequest request) {
        return new BooksResponse(1L, "도서_이름_1");
    }
}
