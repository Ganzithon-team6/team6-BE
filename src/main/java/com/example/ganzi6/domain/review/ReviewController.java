package com.example.ganzi6.domain.review;

import com.example.ganzi6.domain.review.dto.ReviewCreateRequest;
import com.example.ganzi6.domain.review.dto.ReviewDetailResponse;
import com.example.ganzi6.domain.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/market/{marketId}")
    public ResponseEntity<List<ReviewResponse>> getMarketReviews(@PathVariable Long marketId) {

        List<Review> reviews = reviewService.getMarketReviews(marketId);

        List<ReviewResponse> dtoList = reviews.stream()
                .map(ReviewResponse::from)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReviewDetail(@PathVariable Long reviewId) {
        ReviewDetailResponse dto = reviewService.getReviewDetail(reviewId);
        return ResponseEntity.ok(dto);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReviewResponse> create(
            @ModelAttribute ReviewCreateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Review review = reviewService.createReview(request, image);
        return ResponseEntity.ok(ReviewResponse.from(review));
    }
}
