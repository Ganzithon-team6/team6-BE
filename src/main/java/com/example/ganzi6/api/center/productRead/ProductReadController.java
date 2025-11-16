package com.example.ganzi6.api.center.productRead;

import com.example.ganzi6.api.center.productRead.dto.ProductReadDetailResponse;
import com.example.ganzi6.api.center.productRead.dto.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/center/products")
public class ProductReadController {
    private final ProductReadService productReadService;

    @PreAuthorize("hasRole('CENTER')")
    @GetMapping("/get")
    public List<ProductReadResponse> getAllProducts() {
        return productReadService.getProduct();
    }

    @GetMapping("/get/detail/{productId}")
    public ProductReadDetailResponse getProductDetail(@PathVariable Long productId) {
        return productReadService.getDetailProduct(productId);
    }
}
