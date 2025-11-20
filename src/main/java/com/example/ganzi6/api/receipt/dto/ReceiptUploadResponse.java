package com.example.ganzi6.api.receipt.dto;

import com.example.ganzi6.domain.receipt.ReceiptStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptUploadResponse {
    private Long verificationId;
    private ReceiptStatus status;
}
