package com.example.ganzi6.api.market.productCreate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
    private String name;// 음식명
    private String description;
    private Integer count;// 수량
    private String endTime;// String으로 받고 서비스에서 LocalDateTime 변환
    private String imageUrl;
}
