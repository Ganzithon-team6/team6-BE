package com.example.ganzi6.api.auth.AuthController;

import com.example.ganzi6.api.auth.AuthService.AuthService;
import com.example.ganzi6.api.auth.dto.CenterSignupRequest.CenterSignupRequest;
import com.example.ganzi6.api.auth.dto.LoginRequest.LoginRequest;
import com.example.ganzi6.api.auth.dto.LoginResponse.LoginResponse;
import com.example.ganzi6.api.auth.dto.MarketSignupRequest.MarketSignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 가게 회원가입
    @PostMapping("/signup/market")
    public ResponseEntity<Void> signupMarket(@RequestBody @Valid MarketSignupRequest req) {
        authService.signupMarket(req);
        return ResponseEntity.ok().build();
    }

    // 복지시설 회원가입
    @PostMapping("/signup/center")
    public ResponseEntity<Void> signupCenter(@RequestBody @Valid CenterSignupRequest req) {
        authService.signupCenter(req);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest req) {
        LoginResponse res = authService.login(req);
        return ResponseEntity.ok(res);
    }
}