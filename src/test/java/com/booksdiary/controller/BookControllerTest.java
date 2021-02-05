package com.booksdiary.controller;

import com.booksdiary.domain.BookCreateRequest;
import com.booksdiary.domain.BookResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @DisplayName("'/books'로 POST 요청 시, 도서를 생성한다.")
    @Test
    void createTest() throws Exception {
        BookCreateRequest request = new BookCreateRequest(도서_이름_1);
        BookResponse response = new BookResponse(도서_ID_1, 도서_이름_1);
        when(bookService.create(any(BookCreateRequest.class))).thenReturn(response);
        String requestAsString = OBJECT_MAPPER.writeValueAsString(request);

        this.mockMvc.perform(post(API + "/books")
                .content(requestAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    // TODO: 2021/01/27 validation 작동 예시에 대해 테스트코드 작성
    @DisplayName("'/books'로 GET 요청 시, 도서의 목록을 반환한다.")
    @Test
    void listTest() throws Exception {
        List<BookResponse> bookResponses = Arrays.asList(
                new BookResponse(도서_ID_1, 도서_이름_1),
                new BookResponse(도서_ID_2, 도서_이름_2)
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

    @DisplayName("'/books'로 DELETE 요청 시, 해당 도서를 삭제한다.")
    @Test
    void deleteTest() throws Exception {
        doNothing().when(bookService).delete(eq(도서_ID_1));

        this.mockMvc.perform(delete(API + "/books/" + 도서_ID_1)).
                andExpect(status().isNoContent());

        verify(bookService, times(1)).delete(eq(도서_ID_1));
    }
}
