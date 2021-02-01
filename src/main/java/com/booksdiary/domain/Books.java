package com.booksdiary.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// TODO: 2021/01/25 추후 createdDate 작업할때 AuditingEntityLiester
@Entity
// TODO: 2021/01/25 아직 비즈니스 로직이 없다!! 추후 테스트코드 작성
public class Books {
    // TODO: 2021/01/25 JPA 책 바탕으로 ID부분 왜 저렇게 썼나 게시!!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
