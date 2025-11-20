package com.example.ganzi6.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewCreateRequest {
    private Long reservationId;
    private Long marketId;   // 예약 파트가 완성되면 자동으로 가져오도록 수정될 예정
    private Long centerId;
    private String content;
}
