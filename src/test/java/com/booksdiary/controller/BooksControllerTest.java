package com.booksdiary.controller;

import com.booksdiary.domain.BooksCreateRequest;
import com.booksdiary.domain.BooksResponse;
import com.booksdiary.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BooksControllerTest {
    private static final String API = "/api";
    private static final Long 도서_ID_1 = 1L;
    private static final String 도서_이름_1 = "도서_이름_1";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private BooksService booksService;

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
        BooksCreateRequest request = new BooksCreateRequest(도서_이름_1);
        BooksResponse response = new BooksResponse(도서_ID_1, 도서_이름_1);
        when(booksService.create(any(BooksCreateRequest.class))).thenReturn(response);
        String requestAsString = OBJECT_MAPPER.writeValueAsString(request);

        this.mockMvc.perform(post(API + "/books")
                .content(requestAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    // TODO: 2021/01/27 validation 작동 예시에 대해 테스트코드 작성
}
