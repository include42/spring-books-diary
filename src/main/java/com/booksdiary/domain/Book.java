package com.booksdiary.domain;

import lombok.*;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @ISBN
    private String isbn;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime publishDate;
    // TODO: 2021/02/19 실제 API의 양식 확인, 별도의 검증 필요한지 확인(현재날짜보다 뒤여야하나?)

    @NotBlank
    private String publisher;

    @NotBlank
    private String author;
    // TODO: 2021/02/17 저자가 여러 명일때, 일단 지금은 쉼표 표함해서 받음. 추후 리스트화하는 거 고민

    private String translator;

    @NotNull
    @Positive
    private Long price;

    @NotNull
    @URL
    private String thumbnail;

    @NotNull
    @Positive
    private Long page;
}
