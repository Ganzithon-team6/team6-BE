package com.example.ganzi6.domain.receipt.dto;

import com.example.ganzi6.domain.receipt.ReceiptStatus;
import com.example.ganzi6.domain.receipt.ReceiptVerification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiptStatusResponse {
    private Long id;
    private Long reservationId;
    private ReceiptStatus status;
    private String failReason;

    public static ReceiptStatusResponse from(ReceiptVerification v) {
        return ReceiptStatusResponse.builder()
                .id(v.getId())
                .reservationId(v.getReservationId())
                .status(v.getStatus())
                .failReason(v.getFailReason())
                .build();
    }
}
