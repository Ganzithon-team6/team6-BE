package com.example.ganzi6.api.center.Reservation;

import com.example.ganzi6.api.center.Reservation.dto.CenterReservationStatusRequest;
import com.example.ganzi6.api.center.Reservation.dto.CenterReservationStatusResponse;
import com.example.ganzi6.domain.reservation.Reservation;
import com.example.ganzi6.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterReservationStatusService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public List<CenterReservationStatusResponse> getCenterReservationStatus(Long centerId) {

        if (centerId == null) {
            throw new IllegalArgumentException("센터 ID는 필수입니다.");
        }

        // 현재 시간은 서비스에서 직접 계산
        LocalDateTime now = LocalDateTime.now();

        // 1) 센터 기준 예약 전체 조회
        List<Reservation> reservations = reservationRepository.findByCenter_Id(centerId);
        List<CenterReservationStatusResponse> result = new ArrayList<>();

        // 2) 상태 자동 업데이트 & 응답 매핑
        for (Reservation reservation : reservations) {

            var product = reservation.getProduct();
            if (product == null) continue;

            LocalDateTime endTime = product.getEndTime();
            String currentStatus = reservation.getStatus();

            // 마감 시간 지난 "픽업 전" → "픽업 미완료"
            if (endTime != null && endTime.isBefore(now) && "픽업 전".equals(currentStatus)) {
                reservation.setStatus("픽업 미완료");
                reservation.setUpdatedAt(now);
            }

            String marketName = reservation.getMarket() != null
                    ? reservation.getMarket().getName()
                    : (product.getMarket() != null ? product.getMarket().getName() : null);

            result.add(CenterReservationStatusResponse.builder()
                    .reservationId(reservation.getId())
                    .marketName(marketName)
                    .endTime(endTime)
                    .count(reservation.getCount())
                    .status(reservation.getStatus())
                    .reservationTime(reservation.getCreatedAt())
                    .build());
        }

        return result;
    }
}
