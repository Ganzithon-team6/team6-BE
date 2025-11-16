package com.example.ganzi6.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p join fetch p.market")
    List<Product> findAllWithMarket();

    @Query("select p from Product p join fetch p.market where p.id = :productId")
    Optional<Product> findByIdWithMarket(@Param("productId") Long productId);

}
