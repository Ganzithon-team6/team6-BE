package com.example.ganzi6.api.center.productRead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewSummaryResponse {
    // 리뷰 내용
    private String content;
    // 리뷰 작성 시간 (문자열로 포맷해서 내려줄 예정)
    private String createdAt;
    // 이 가게(마켓)에 달린 전체 리뷰 수
    private Long marketReviewCount;
    // 리뷰를 남긴 센터 이름
    private String centerName;
    // 이 리뷰와 연결된 예약 → 상품 이름
    private String productName;
    private String imageUrl;
}
