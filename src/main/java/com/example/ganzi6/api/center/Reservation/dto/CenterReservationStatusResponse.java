package com.example.ganzi6.api.center.Reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterReservationStatusResponse {
    private Long reservationId;
    private Long marketId;
    private String marketName;
    private LocalDateTime endTime;
    private Integer count;
    private String status;
    //예약 시간
    private LocalDateTime reservationTime;
}
