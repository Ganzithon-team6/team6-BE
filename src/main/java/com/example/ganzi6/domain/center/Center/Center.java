package com.example.ganzi6.domain.center.Center;

import com.example.ganzi6.domain.product.Product;
import com.example.ganzi6.domain.reservation.Reservation;
import com.example.ganzi6.domain.user.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    private boolean verified; // 공공데이터 검증 여부 (검증내역값)

    @Column(length = 200)
    private String description;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Reservation> reservations = new ArrayList<>();

    public void verify() {
        this.verified = true;
    }
}

