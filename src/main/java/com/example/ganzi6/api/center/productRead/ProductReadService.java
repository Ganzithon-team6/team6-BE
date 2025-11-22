package com.example.ganzi6.api.center.productRead;


import com.example.ganzi6.api.center.productRead.dto.ProductReadDetailResponse;
import com.example.ganzi6.api.center.productRead.dto.ProductReadResponse;
import com.example.ganzi6.api.center.productRead.dto.ProductReviewSummaryResponse;
import com.example.ganzi6.domain.center.Center.Center;
import com.example.ganzi6.domain.center.CenterRepository.CenterRepository;
import com.example.ganzi6.domain.market.Market.Market;
import com.example.ganzi6.domain.market.MarketRepository.MarketRepository;
import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.product.ProductRepository;
import com.example.ganzi6.domain.reservation.Reservation;
import com.example.ganzi6.domain.reservation.ReservationRepository;
import com.example.ganzi6.domain.review.Review;
import com.example.ganzi6.domain.review.ReviewRepository;
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

    private final ReviewRepository reviewRepository;
    private final CenterRepository centerRepository;
    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<ProductReadResponse> getProduct(){ //복지시설 홈에서의 음식 조회
        List<Product> products = productRepository.findAllWithMarket();

        return products.stream()
                .map(product -> new ProductReadResponse(
                        product.getId(),
                        product.getName(),                    // 물건 이름
                        product.getImageUrl(),                // 이미지 URL
                        product.getMarket().getAddress(),     // 마켓 주소
                        formatEndTime(product.getEndTime()),   // LocalDateTime → String
                        product.getCount()
                ))
                .toList();
    }
    @Transactional(readOnly = true)
    public ProductReadDetailResponse getDetailProduct(Long productId){
        Product product = productRepository.findByIdWithMarket(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 물품이 존재하지 않습니다. id=" + productId));

        Long marketId = product.getMarket().getId();

        // 2) 이 마켓의 모든 리뷰 조회
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new IllegalArgumentException("마켓을 찾을 수 없습니다. id=" + marketId));

        List<Review> reviews = reviewRepository.findByMarket(market);

        long marketReviewCount = reviews.size();   // 가게 기준 리뷰 수

        // 3) 리뷰 → DTO 변환
        List<ProductReviewSummaryResponse> reviewDtos = reviews.stream()
                .map(review -> {

                    // 3-1) 센터 이름
                    String centerName = null;
                    Center center = review.getCenter();
                    if (center != null) {
                        centerName = center.getName();
                    }

                    // 3-2) 예약 → 상품 이름
                    String productNameFromReservation = null;
                    Reservation reservation = review.getReservation();
                    if (reservation != null && reservation.getProduct() != null) {
                        productNameFromReservation = reservation.getProduct().getName();
                    }

                    return new ProductReviewSummaryResponse(
                            review.getContent(),                      // content
                            formatDateTime(review.getCreatedAt()),    // createdAt
                            marketReviewCount,                        // 해당 가게 전체 리뷰 수
                            centerName,                               // 센터 이름
                            productNameFromReservation,                // 예약과 연결된 상품 이름
                            review.getImageUrl()
                    );
                })
                .toList();

        // 4) 기존 상세 정보 + 리뷰 리스트까지 담아서 반환
        return new ProductReadDetailResponse(
                product.getId(),                       // productId
                product.getMarket().getId(),           // marketId
                product.getMarket().getName(),         // marketName
                product.getDescription(),              // productDescription
                product.getName(),                     // productName
                product.getImageUrl(),                 // imageUrl
                product.getCount(),                    // count
                formatEndTime(product.getEndTime()),   // endTime
                reviewDtos                             // 새로 추가된 리뷰 리스트
        );
    }

    private String formatEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            return null;
        }
        return endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // 리뷰 시간 포맷용
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
