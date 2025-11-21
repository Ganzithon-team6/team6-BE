package com.example.ganzi6.domain.review;

import com.example.ganzi6.domain.market.Market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 마켓(가게)에 달린 리뷰 전체 조회 (최신순)
    List<Review> findByMarketOrderByCreatedAtDesc(Market market);

    // 단순히 마켓 기준으로 다 조회 (정렬 없이)
    List<Review> findByMarket(Market market);
}
