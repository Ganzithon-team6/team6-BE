package com.example.ganzi6.api.center.productRead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReadDetailResponse {
    private Long productId;
    private Long marketId;
    private String marketName; //물건 이름
    private String productDescription;
    private String productName; // 마켓 이름
    private String imageUrl;//물건 사진
    private Integer count;
    private String endTime; // 물건 마감기한
    //소망님 리뷰 작업 이후 리뷰 내역 추가
}
