package com.example.ganzi6.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewImageStorageService {
    @Value("${app.review-upload-dir:./uploads/reviews}")
    private String uploadDir;

    public String store(MultipartFile file) {

        try {
            Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dirPath);

            String original = file.getOriginalFilename();
            String ext = "";

            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf("."));
            }

            String filename = UUID.randomUUID() + ext;

            Path target = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // DB에는 상대 경로만 저장!
            return "/reviews/" + filename;

        } catch (Exception e) {
            throw new RuntimeException("리뷰 이미지 저장 실패", e);
        }
    }
}