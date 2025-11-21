package com.example.ganzi6.domain.receipt;

import com.example.ganzi6.api.receipt.dto.ReceiptUploadResponse;
import com.example.ganzi6.domain.receipt.dto.ReceiptStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;

    /**
     * 영수증 이미지 업로드 + 인증 요청 (PENDING 생성)
     *
     * form-data:
     *  - reservationId: Long
     *  - file: 이미지 파일
     */

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptStatusResponse> getStatus(@PathVariable Long id) {
        ReceiptVerification v = receiptService.getVerification(id);
        return ResponseEntity.ok(ReceiptStatusResponse.from(v));
    }

    /**
     * 영수증 이미지 업로드 + 인증 요청
     *
     * form-data:
     *  - reservationId: Long
     *  - file: 이미지 파일
     */
    @PostMapping(
            value = "/verify",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ReceiptUploadResponse> uploadAndVerify(
            @RequestParam("reservationId") Long reservationId,
            @RequestPart("file") MultipartFile file
    ) {
        ReceiptUploadResponse response =
                receiptService.uploadAndVerify(reservationId, file);

        return ResponseEntity.ok(response);
    }
}