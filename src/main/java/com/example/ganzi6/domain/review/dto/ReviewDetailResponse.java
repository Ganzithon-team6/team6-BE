package com.example.ganzi6.domain.review.dto;

import com.example.ganzi6.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDetailResponse {

    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    private Long reservationId;
    private Long marketId;
    private Long centerId;

    private String marketName;
    private String centerName;
    private String productName;   // 예약 → 상품 이름

    public static ReviewDetailResponse from(Review review) {

        var reservation = review.getReservation();
        var market = review.getMarket();
        var center = review.getCenter();

        Long reservationId = (reservation != null) ? reservation.getId() : null;
        Long marketId = (market != null) ? market.getId() : null;
        Long centerId = (center != null) ? center.getId() : null;

        String marketName = (market != null) ? market.getName() : null;
        String centerName = (center != null) ? center.getName() : null;
        String productName = (reservation != null && reservation.getProduct() != null)
                ? reservation.getProduct().getName()
                : null;

        return new ReviewDetailResponse(
                review.getId(),
                review.getContent(),
                review.getImageUrl(),
                review.getCreatedAt(),
                reservationId,
                marketId,
                centerId,
                marketName,
                centerName,
                productName
        );
    }
}