package com.example.ganzi6.api.verification;

import com.example.ganzi6.domain.center.Center.Center;
import com.example.ganzi6.domain.center.CenterRepository.CenterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterVerificationService {

    private final CenterRepository centerRepository;
    private final SafetyDataClient safetyDataClient;

    @Transactional
    public void verifyCenterIfNeeded(Long centerId) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new IllegalArgumentException("센터를 찾을 수 없습니다."));

        // 이미 검증된 센터면 그냥 통과
        if (center.isVerified()) {
            return;
        }

        boolean exists = safetyDataClient.existsFacility(center.getName());
        if (!exists) {
            // 공공데이터에 없으면 예약 막기
            throw new IllegalStateException("공공데이터에 없는 복지시설이라 예약이 불가합니다.");
        }

        // 공공데이터에 존재 → 검증 상태로 변경
        center.verify();
    }
}
