package com.example.ganzi6.domain.receipt;

import com.example.ganzi6.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptVerificationRepository extends JpaRepository<ReceiptVerification, Long> {
    Optional<ReceiptVerification> findFirstByReservationOrderByCreatedAtDesc(Reservation reservation);
}
