package com.example.ganzi6.api.center.productRead;


import com.example.ganzi6.api.center.productRead.dto.ProductReadDetailResponse;
import com.example.ganzi6.api.center.productRead.dto.ProductReadResponse;
import com.example.ganzi6.domain.market.MarketRepository.MarketRepository;
import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReadService {
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;

    @Transactional(readOnly = true)
    public List<ProductReadResponse> getProduct(){ //복지시설 홈에서의 음식 조회
        List<Product> products = productRepository.findAllWithMarket();

        return products.stream()
                .map(product -> new ProductReadResponse(
                        product.getId(),
                        product.getName(),                    // 물건 이름
                        product.getImageUrl(),                // 이미지 URL
                        product.getMarket().getAddress(),     // 마켓 주소
                        formatEndTime(product.getEndTime())   // LocalDateTime → String
                ))
                .toList();
    }
    @Transactional(readOnly = true)
    public ProductReadDetailResponse getDetailProduct(Long productId){
        Product product = productRepository.findByIdWithMarket(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 물품이 존재하지 않습니다. id=" + productId));

        return new ProductReadDetailResponse(
                product.getId(),                         // productId
                product.getMarket().getId(),             // marketId
                product.getMarket().getName(),           // marketName
                product.getDescription(),                // productDescription
                product.getName(),                       // productName
                product.getImageUrl(),                   // imageUrl
                product.getCount(),
                formatEndTime(product.getEndTime())      // endTime
        );

    }





    private String formatEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            return null;
        }
        return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
