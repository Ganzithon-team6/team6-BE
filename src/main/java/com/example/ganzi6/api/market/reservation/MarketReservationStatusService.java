package com.example.ganzi6.api.market.reservation;

import com.example.ganzi6.api.market.reservation.dto.MarketReservationStatusResponse;
import com.example.ganzi6.domain.center.CenterRepository.CenterRepository;
import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.product.ProductRepository;
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
public class MarketReservationStatusService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public List<MarketReservationStatusResponse> getMarketReservationStatus(Long marketId) {

        // 1) 해당 마켓의 모든 예약 조회 (상품, 센터는 지연 로딩)
        List<Reservation> reservations = reservationRepository.findByMarket_Id(marketId);
        List<MarketReservationStatusResponse> result = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Product product = reservation.getProduct();
            if (product == null) continue;

            // 예약에 연결된 센터
            var center = reservation.getCenter();
            String centerName = (center != null) ? center.getName() : null;

            result.add(
                    MarketReservationStatusResponse.builder()
                            .centerName(centerName)
                            .endTime(product.getEndTime())
                            .count(reservation.getCount())
                            .status(reservation.getStatus())
                            .reservationTime(reservation.getCreatedAt())
                            .build()
            );
        }

        return result;
    }
}
