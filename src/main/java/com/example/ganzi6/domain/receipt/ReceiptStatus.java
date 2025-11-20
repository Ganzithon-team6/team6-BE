package com.example.ganzi6.domain.receipt;

public enum ReceiptStatus {
    PENDING,   // 업로드만 된 상태 (OCR 처리 전/중)
    SUCCESS,   // 인증 성공
    FAIL
}
