package com.booksdiary.domain.book.dto.kakao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoBookDto {
    private List<KakaoBookDocumentDto> documents;
    private KakaoBookMetadataDto meta;
}
