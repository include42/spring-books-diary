package com.booksdiary.controller;

import com.booksdiary.controller.dto.BookResponseDto;
import com.booksdiary.service.BookService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// TODO: 2021/02/17 Generic을 이용한 Post, Delete 테스트 하기
@WebMvcTest
public class BookControllerTest {
    private static final String API = "/api";
    private static final Long 도서_ID_1 = 1L;
    private static final Long 도서_ID_2 = 2L;
    private static final String 도서_이름_1 = "도서_이름_1";
    private static final String 도서_이름_2 = "도서_이름_2";
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
    void listTest() throws Exception {
        List<BookResponseDto> bookResponses = Arrays.asList(
                new BookResponseDto(도서_ID_1, 도서_이름_1),
                new BookResponseDto(도서_ID_2, 도서_이름_2)
        );
        when(bookService.list()).thenReturn(bookResponses);

        this.mockMvc.perform(get(API + "/books")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(도서_ID_1))
                .andExpect(jsonPath("$[1].id").value(도서_ID_2))
                .andExpect(jsonPath("$[0].name").value(도서_이름_1))
                .andExpect(jsonPath("$[1].name").value(도서_이름_2));
    }
}
