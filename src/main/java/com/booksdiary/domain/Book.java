package com.booksdiary.domain;

import lombok.*;
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

    @NotBlank
    private String isbn;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime publishDate;

    @NotBlank
    private String publisher;

    @NotNull
    private String author;
    // TODO: 2021/02/17 저자가 여러 명일때, 일단 지금은 쉼표 표함해서 받음. 추후 리스트화하는 거 고민

    private String translator;

    @Positive
    private Long price;

    @NotBlank
    private String thumbnail;

    @Positive
    private Long page;
}
