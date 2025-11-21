package com.example.ganzi6.domain.review;

import com.example.ganzi6.domain.market.Market.Market;
import com.example.ganzi6.domain.market.MarketRepository.MarketRepository;
import com.example.ganzi6.domain.receipt.ReceiptStatus;
import com.example.ganzi6.domain.receipt.ReceiptVerification;
import com.example.ganzi6.domain.receipt.ReceiptVerificationRepository;
import com.example.ganzi6.domain.reservation.Reservation;
import com.example.ganzi6.domain.reservation.ReservationRepository;
import com.example.ganzi6.domain.review.dto.ReviewCreateRequest;
import com.example.ganzi6.domain.review.dto.ReviewDetailResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReceiptVerificationRepository receiptVerificationRepository;
    private final ReviewImageStorageService reviewImageStorageService;
    private final ReservationRepository reservationRepository;
    private final MarketRepository marketRepository;

    @Transactional
    public Review createReview(ReviewCreateRequest request,
                               MultipartFile imageFile) {

        Long reservationId = request.getReservationId();

        // 예약 엔티티 조회
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다. id=" + reservationId));

        // 해당 예약에 대한 가장 최근 영수증 인증 조회
        ReceiptVerification verification =
                receiptVerificationRepository.findFirstByReservationOrderByCreatedAtDesc(reservation)
                        .orElseThrow(() -> new IllegalArgumentException("해당 예약에 대한 영수증 인증이 없습니다."));

        // 영수증 인증이 SUCCESS 인 경우만 리뷰 작성 허용
        if (verification.getStatus() != ReceiptStatus.SUCCESS) {
            throw new IllegalStateException("영수증 인증이 완료된 경우에만 리뷰를 작성할 수 있습니다.");
        }

        // 이미지 파일 있으면 저장
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = reviewImageStorageService.store(imageFile);
        }

        // 리뷰 저장
        Review review = Review.builder()
                .reservation(reservation)
                .market(reservation.getMarket())
                .center(reservation.getCenter())
                .content(request.getContent())
                .imageUrl(imageUrl)
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public List<Review> getMarketReviews(Long marketId) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다. id=" + marketId));

        return reviewRepository.findByMarketOrderByCreatedAtDesc(market);
    }

    @Transactional
    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + id));
    }

    @Transactional
    public ReviewDetailResponse getReviewDetail(Long id) {
        Review review = getReview(id);   // 위 메서드 재사용
        return ReviewDetailResponse.from(review);
    }
}
