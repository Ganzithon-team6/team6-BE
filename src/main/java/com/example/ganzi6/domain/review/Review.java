package com.example.ganzi6.domain.review;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    private Long reservationId;
    private Long marketId;
    private Long centerId;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
