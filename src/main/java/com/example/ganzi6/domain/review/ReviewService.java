package com.example.ganzi6.domain.review;

import com.example.ganzi6.domain.receipt.ReceiptStatus;
import com.example.ganzi6.domain.receipt.ReceiptVerification;
import com.example.ganzi6.domain.receipt.ReceiptVerificationRepository;
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

    @Transactional
    public Review createReview(Long reservationId,
                               Long marketId,
                               Long centerId,
                               String content,
                               MultipartFile imageFile) {

        // 영수증 인증 조회
        ReceiptVerification verification =
                receiptVerificationRepository.findFirstByReservationIdOrderByCreatedAtDesc(reservationId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 예약에 대한 영수증 인증이 없습니다."));

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
                .reservationId(reservationId)
                .marketId(marketId)
                .centerId(centerId)
                .content(content)
                .imageUrl(imageUrl)
                .build();

        return reviewRepository.save(review);
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
    }

    public List<Review> getMarketReviews(Long marketId) {
        return reviewRepository.findByMarketIdOrderByCreatedAtDesc(marketId);
    }

}