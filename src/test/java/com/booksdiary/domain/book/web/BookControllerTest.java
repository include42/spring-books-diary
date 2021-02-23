package com.booksdiary.domain.book.web;

import com.booksdiary.domain.book.exception.BookNotFoundException;
import com.booksdiary.domain.book.service.BookService;
import com.booksdiary.domain.book.service.dto.BookResponseServiceDto;
import com.booksdiary.global.error.ErrorCode;
import com.booksdiary.utils.BookGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static com.booksdiary.utils.BookGenerator.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: 2021/02/17 Generic을 이용한 Post, Delete 테스트 하기
@WebMvcTest
public class BookControllerTest {
    private static final String API = "/api";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("'/books'로 GET 요청 시, 도서의 목록을 반환한다.")
    @Test
    void getBooksTest() throws Exception {
        List<BookResponseServiceDto> bookResponses = BookGenerator.createBooks()
                .stream()
                .map(BookResponseServiceDto::new)
                .collect(Collectors.toList());
        when(bookService.getBooks()).thenReturn(bookResponses);

        this.mockMvc.perform(get(API + "/books")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(도서_ID_1))
                .andExpect(jsonPath("$[1].id").value(도서_ID_2))
                .andExpect(jsonPath("$[0].name").value(도서_이름_1))
                .andExpect(jsonPath("$[1].name").value(도서_이름_2));
    }

    @DisplayName("'/books/{id}'로 GET 요청 시, 해당 도서를 반환한다.")
    @Test
    void readBookTest() throws Exception {
        BookResponseServiceDto bookResponse = new BookResponseServiceDto(BookGenerator.createBook());
        when(bookService.getBook(도서_ID_1)).thenReturn(bookResponse);

        this.mockMvc.perform(get(API + "/books/" + 도서_ID_1)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(도서_ID_1))
                .andExpect(jsonPath("$.name").value(도서_이름_1));
    }

    @DisplayName("예외 테스트 : '/books/{id}'로 GET 요청 시, 해당 도서가 없다면 예외를 발생시킨다.")
    @Test
    public void readBookNotFoundException() throws Exception {
        when(bookService.getBook(도서_ID_1)).thenThrow(new BookNotFoundException(도서_ID_1));

        //then
        this.mockMvc.perform(get(API + "/books/" + 도서_ID_1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.ENTITY_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.ENTITY_NOT_FOUND.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.ENTITY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
    }
}
