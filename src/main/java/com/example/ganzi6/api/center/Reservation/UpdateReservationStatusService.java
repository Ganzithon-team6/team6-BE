package com.example.ganzi6.api.center.Reservation;

import com.example.ganzi6.domain.reservation.Reservation;
import com.example.ganzi6.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateReservationStatusService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public void completePickup(Long reservationId) {

        // 1) 예약 조회 (없으면 예외)
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. id=" + reservationId));

        String currentStatus = reservation.getStatus();

        // 2) 현재 상태가 "픽업 전"일 때만 "픽업 완료"로 변경
        if ("픽업 전".equals(currentStatus)) {
            reservation.setStatus("픽업 완료");          // 상태 변경
            reservation.setUpdatedAt(LocalDateTime.now()); // 수정 시각 갱신

        }
        if ("픽업 완료".equals(currentStatus)) {
            reservation.setStatus("작성 완료");          // 상태 변경
            reservation.setUpdatedAt(LocalDateTime.now()); // 수정 시각 갱신

        }


    }
}
