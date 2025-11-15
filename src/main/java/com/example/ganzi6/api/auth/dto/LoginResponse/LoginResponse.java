package com.example.ganzi6.api.auth.dto.LoginResponse;

import com.example.ganzi6.domain.user.Role.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private Long userId;
    private Role role;
}