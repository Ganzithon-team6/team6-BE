package com.example.ganzi6.api.center.productRead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReadResponse {// 그냥 전체 조회하고 프론트에서 엔드타임으로 정렬하시는걸로 생각.
    private Long productId;
    private String name; //물건 이름
    private String imageUrl;//물건 사진
    private String address; // 마켓 주소
    private String endTime; // 물건 마감기한
}
