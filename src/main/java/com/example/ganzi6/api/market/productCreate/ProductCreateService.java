package com.example.ganzi6.api.market.productCreate;

import com.example.ganzi6.api.market.productCreate.dto.ProductCreateRequest;
import com.example.ganzi6.domain.market.Market.Market;
import com.example.ganzi6.domain.market.MarketRepository.MarketRepository;
import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductCreateService {
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;

    public Long createProduct(Long marketId, ProductCreateRequest request) {

        // 1) Market 조회
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

        // 2) endTime 변환 (String → LocalDateTime)
        LocalDateTime endTime = LocalDateTime.parse(request.getEndTime());

        // 3) Product 엔티티 생성
        Product product = Product.builder()
                .market(market)
                .center(null)// 가게가 등록하는 거라 center는 null
                .name(request.getName())
                .description(request.getDescription())
                .endTime(endTime)
                .count(request.getCount())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 4) DB 저장
        productRepository.save(product);

        return product.getId();
    }
}
