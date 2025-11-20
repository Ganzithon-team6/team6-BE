package com.example.ganzi6.domain.receipt;

import com.example.ganzi6.api.receipt.dto.ReceiptUploadResponse;
import com.example.ganzi6.infra.ocr.OcrClient;
import com.example.ganzi6.infra.ocr.dto.UpstageOcrResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final ReceiptVerificationRepository receiptVerificationRepository;
    private final ReceiptStorageService receiptStorageService;
    private final OcrClient ocrClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*@Transactional
    public ReceiptUploadResponse uploadReceipt(Long reservationId,
                                               MultipartFile file) {

        //TODO: reservationId 유효성 검증 (Reservation 존재 여부 체크) 하면 더 좋음(근데 안할듯)

        // 파일 저장
        String imageUrl = receiptStorageService.store(file);

        // PENDING 상태로 ReceiptVerification 생성
        ReceiptVerification verification = ReceiptVerification.builder()
                .reservationId(reservationId)
                .imageUrl(imageUrl)
                .status(ReceiptStatus.PENDING)
                .build();

        ReceiptVerification saved = receiptVerificationRepository.save(verification);

        return ReceiptUploadResponse.builder()
                .verificationId(saved.getId())
                .status(saved.getStatus())
                .build();
    }*/
    /**
     * 영수증 업로드 + 즉시 OCR 실행 + SUCCESS/FAIL 판정
     */
    @Transactional
    public ReceiptUploadResponse uploadAndVerify(Long reservationId,
                                                 String expectedStoreName,
                                                 MultipartFile file) {

        // 파일 저장
        String imageUrl = receiptStorageService.store(file);

        // PENDING 상태로 저장
        ReceiptVerification verification = ReceiptVerification.builder()
                .reservationId(reservationId)
                .imageUrl(imageUrl)
                .status(ReceiptStatus.PENDING)
                .build();

        receiptVerificationRepository.save(verification);

        // OCR 실행 + 판정
        runOcrAndUpdate(verification, expectedStoreName);

        // 결과 반환
        return ReceiptUploadResponse.builder()
                .verificationId(verification.getId())
                .status(verification.getStatus())
                .build();
    }

    /**
     * 실제 OCR 호출 + SUCCESS/FAIL 업데이트
     */
    private void runOcrAndUpdate(ReceiptVerification verification,
                                 String expectedStoreName) {

        try {
            // OCR 호출
            String ocrJson = ocrClient.processImage(verification.getImageUrl());

            // JSON → 객체 변환 후 text 추출
            UpstageOcrResponse resp =
                    objectMapper.readValue(ocrJson, UpstageOcrResponse.class);

            String fullText = resp.getText() != null ? resp.getText() : "";

            // 간단 매칭 규칙: 가게 이름이 포함되어 있으면 성공
            boolean matched = false;
            if (expectedStoreName != null && !expectedStoreName.isBlank()) {
                matched = fullText.toLowerCase().replace(" ", "")
                        .contains(expectedStoreName.toLowerCase().replace(" ", ""));
            }

            if (matched) {
                verification.markSuccess(ocrJson);
            } else {
                verification.markFail(ocrJson, "가게명이 영수증에서 확인되지 않음");
            }

        } catch (JsonProcessingException e) {
            verification.markFail(null, "OCR 결과 파싱 실패");
        } catch (Exception e) {
            verification.markFail(null, "OCR 호출 실패: " + e.getMessage());
        }
    }

    /**
     * 인증 상태 조회용
     */
    @Transactional
    public ReceiptVerification getVerification(Long id) {
        return receiptVerificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("영수증 인증 정보를 찾을 수 없습니다. id=" + id));
    }
}
