package com.example.ganzi6.infra.ocr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpstageOcrResponse {
    private String text;  // 응답 JSON에서 text 필드만 사용
}
