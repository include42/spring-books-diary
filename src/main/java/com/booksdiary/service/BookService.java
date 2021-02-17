package com.booksdiary.service;

import com.booksdiary.controller.dto.BookResponseDto;
import com.booksdiary.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookResponseDto> list() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
    }
}
