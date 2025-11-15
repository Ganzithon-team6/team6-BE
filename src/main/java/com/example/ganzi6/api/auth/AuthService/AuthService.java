package com.example.ganzi6.api.auth.AuthService;

import com.example.ganzi6.api.auth.dto.CenterSignupRequest.CenterSignupRequest;
import com.example.ganzi6.api.auth.dto.LoginRequest.LoginRequest;
import com.example.ganzi6.api.auth.dto.LoginResponse.LoginResponse;
import com.example.ganzi6.api.auth.dto.MarketSignupRequest.MarketSignupRequest;
import com.example.ganzi6.domain.center.Center.Center;
import com.example.ganzi6.domain.center.CenterRepository.CenterRepository;
import com.example.ganzi6.domain.market.Market.Market;
import com.example.ganzi6.domain.market.MarketRepository.MarketRepository;
import com.example.ganzi6.domain.user.Role.Role;
import com.example.ganzi6.domain.user.User.User;
import com.example.ganzi6.domain.user.UserRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MarketRepository marketRepository;
    private final CenterRepository centerRepository;

    // 가게 회원가입
    @Transactional
    public void signupMarket(MarketSignupRequest req) {

        User user = User.builder()
                .loginId(req.getLoginId())
                .password(req.getPassword())       // TODO: 나중에 암호화
                .role(Role.MARKET)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        Market market = Market.builder()
                .name(req.getName())
                .address(req.getAddress())
                .description(req.getDescription())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        marketRepository.save(market);
    }

    // 복지시설 회원가입
    @Transactional
    public void signupCenter(CenterSignupRequest req) {

        User user = User.builder()
                .loginId(req.getLoginId())
                .password(req.getPassword())
                .role(Role.CENTER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        Center center = Center.builder()
                .name(req.getName())
                .address(req.getAddress())
                .description(req.getDescription())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        
        centerRepository.save(center);
    }

    // 로그인
    @Transactional
    public LoginResponse login(LoginRequest req) {

        User user = userRepository
                .findByLoginIdAndRole(req.getLoginId(), req.getRole())
                .orElseThrow(() ->
                        new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!user.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return LoginResponse.builder()
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }
}