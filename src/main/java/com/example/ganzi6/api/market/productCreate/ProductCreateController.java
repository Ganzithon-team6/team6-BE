package com.example.ganzi6.api.market.productCreate;

import com.example.ganzi6.api.market.productCreate.dto.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class ProductCreateController {
    private final ProductCreateService productCreateService;

    @PreAuthorize("hasRole('MARKET')")
    @PostMapping("/{marketId}/products")
    public ResponseEntity<?> createProduct(@PathVariable Long marketId,
                                           @RequestBody ProductCreateRequest request) {
        productCreateService.createProduct(marketId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
