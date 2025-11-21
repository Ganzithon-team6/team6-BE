package com.example.ganzi6.domain.receipt;

import com.example.ganzi6.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReceiptVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 예약에 대한 영수증인지 (FK: reservation.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 영수증 이미지 경로
    @Column(nullable = false)
    private String imageUrl;

    // 업스테이지 OCR 응답 raw JSON
    @Lob
    private String ocrResultJson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReceiptStatus status; // PENDING / SUCCESS / FAIL

    private String failReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markPending() {
        this.status = ReceiptStatus.PENDING;
        this.failReason = null;
    }

    public void markSuccess(String ocrResultJson) {
        this.status = ReceiptStatus.SUCCESS;
        this.ocrResultJson = ocrResultJson;
        this.failReason = null;
    }

    public void markFail(String ocrResultJson, String failReason) {
        this.status = ReceiptStatus.FAIL;
        this.ocrResultJson = ocrResultJson;
        this.failReason = failReason;
    }

    public Long getReservationId() {
        return reservation != null ? reservation.getId() : null;
    }
}
