package com.example.ganzi6.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/market/{marketId}")
    public ResponseEntity<?> getMarketReviews(@PathVariable Long marketId) {
        return ResponseEntity.ok(reviewService.getMarketReviews(marketId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestParam("reservationId") Long reservationId,
            @RequestParam("marketId") Long marketId,
            @RequestParam("centerId") Long centerId,
            @RequestParam("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Review review = reviewService.createReview(
                reservationId,
                marketId,
                centerId,
                content,
                image
        );
        return ResponseEntity.ok(review);
    }
}
