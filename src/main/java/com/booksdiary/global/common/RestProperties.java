package com.booksdiary.global.common;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("rest")
public class RestProperties {
    private final Map<String, String> kakao;

    public String getKakaoAuthorization() {
        return kakao.get("authorization");
    }

    public String getKakaoUrl() {
        return kakao.get("url");
    }
}
