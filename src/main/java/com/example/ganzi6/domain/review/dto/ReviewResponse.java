package com.example.ganzi6.domain.review.dto;

import com.example.ganzi6.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Long reservationId;
    private Long marketId;
    private Long centerId;

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getImageUrl(),
                review.getCreatedAt(),
                review.getReservation().getId(),
                review.getMarket().getId(),
                review.getCenter().getId()
        );
    }
}

