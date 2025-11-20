package com.example.ganzi6.infra.ocr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class OcrClient {

    @Value("${upstage.api.key}")
    private String apiKey;

    @Value("${upstage.api.url}")
    private String ocrUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 서버에 저장된 이미지 파일 경로를 받아 Upstage OCR 호출
     * @param imagePath ReceiptVerification.imageUrl (서버 경로)
     * @return Upstage OCR 응답 JSON(String)
     */
    public String processImage(String imagePath) {

        File file = new File(imagePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("이미지 파일을 찾을 수 없습니다: " + imagePath);
        }

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + apiKey);

        // 바디 설정 (model, document)
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", "ocr");
        body.add("document", new FileSystemResource(file)); // 파일 첨부

        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        // 호출
        ResponseEntity<String> response =
                restTemplate.postForEntity(ocrUrl, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Upstage OCR 호출 실패: " + response.getStatusCode());
        }

        return response.getBody(); // OCR 결과 JSON 문자열
    }
}