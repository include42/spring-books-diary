package com.booksdiary.service;

import com.booksdiary.domain.BookRepository;
import com.booksdiary.service.dto.BookResponseServiceDto;
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
    public List<BookResponseServiceDto> list() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponseServiceDto::new)
                .collect(Collectors.toList());
    }
}
