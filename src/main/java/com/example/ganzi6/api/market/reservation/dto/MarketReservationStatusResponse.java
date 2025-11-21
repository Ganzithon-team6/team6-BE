package com.example.ganzi6.api.market.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketReservationStatusResponse {
    // 센터 이름
    private String centerName;
    // 상품 마감 시간
    private LocalDateTime endTime;
    // 예약 수량
    private Integer count;
    // 예약 상태(픽업 전 / 픽업 미완료 / 픽업 완료)
    private String status;
    //예약 시간
    private LocalDateTime reservationTime;
}
