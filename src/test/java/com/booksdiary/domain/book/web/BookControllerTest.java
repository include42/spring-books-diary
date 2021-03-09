package com.booksdiary.domain.book.web;

import com.booksdiary.domain.book.dto.BookCreateRequestDto;
import com.booksdiary.domain.book.dto.BookCreateRequestServiceDto;
import com.booksdiary.domain.book.dto.BookResponseServiceDto;
import com.booksdiary.domain.book.exception.BookNotFoundException;
import com.booksdiary.domain.book.service.BookClient;
import com.booksdiary.domain.book.service.BookService;
import com.booksdiary.global.error.ErrorCode;
import com.booksdiary.utils.BookGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.booksdiary.utils.BookGenerator.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: 2021/02/17 Generic을 이용한 Post, Delete 테스트 하기
@WebMvcTest
public class BookControllerTest {
    private static final String API = "/api";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private BookService bookService;
    @MockBean
    private BookClient bookClient;

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
                .andExpect(jsonPath("$[0].title").value(도서_제목_1))
                .andExpect(jsonPath("$[1].title").value(도서_제목_2))
                .andDo(print());
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
                .andExpect(jsonPath("$.title").value(도서_제목_1))
                .andDo(print());
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
                .andExpect(jsonPath("errors").isEmpty())
                .andDo(print());
    }

    @DisplayName("'/books'로 POST 요청 시, 도서를 생성한다.")
    @Test
    void createBookTest() throws Exception {
        BookCreateRequestDto bookCreateRequestDto = new BookCreateRequestDto(도서_ISBN);
        BookResponseServiceDto bookResponse = new BookResponseServiceDto(BookGenerator.createBook());
        BookCreateRequestServiceDto bookCreateRequestServiceDto = BookGenerator.createBookCreateRequestServiceDto();
        String request = OBJECT_MAPPER.writeValueAsString(bookCreateRequestDto);
        when(bookClient.getBookDocuments(도서_ISBN)).thenReturn(bookCreateRequestServiceDto);
        when(bookService.createBook(any(BookCreateRequestServiceDto.class))).thenReturn(bookResponse);

        this.mockMvc.perform(post(API + "/books/")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(도서_ID_1))
                .andExpect(jsonPath("$.title").value(도서_제목_1))
                .andDo(print());
    }

    @DisplayName("예외 테스트 : '/books'로 POST 요청 시, 잘못된 ISBN이 요청으로 전달되면 예외를 발생시킨다.")
    @Test
    void createBookInvalidISBNExceptionTest() throws Exception {
        BookCreateRequestDto bookCreateRequestDto = new BookCreateRequestDto(Strings.EMPTY);
        String request = OBJECT_MAPPER.writeValueAsString(bookCreateRequestDto);

        this.mockMvc.perform(post(API + "/books/")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    Objects.requireNonNull(exception);
                    assertTrue(exception.getClass().isAssignableFrom(MethodArgumentNotValidException.class));
                })
                .andExpect(jsonPath("$.errors[0].reason").value("invalid ISBN"))
                .andDo(print());
    }
}
