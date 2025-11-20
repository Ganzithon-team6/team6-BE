package com.example.ganzi6.domain.receipt;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class ReceiptStorageService {
    @Value("${app.receipt-upload-dir:./uploads/receipts}")
    private String uploadDir;

    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일입니다.");
        }

        try {
            // 디렉터리 없으면 생성
            Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dirPath);

            // 파일명: UUID + 원본 확장자
            String originalFilename = file.getOriginalFilename();
            String ext = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID() + ext;

            Path targetPath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String storedPath = targetPath.toString();
            log.info("Receipt stored at: {}", storedPath);

            // 지금은 로컬 경로 문자열을 imageUrl로 사용
            return storedPath;

        } catch (IOException e) {
            throw new RuntimeException("영수증 파일 저장 중 오류가 발생했습니다.", e);
        }
    }
}
