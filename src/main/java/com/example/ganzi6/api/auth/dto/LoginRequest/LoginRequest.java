package com.example.ganzi6.api.auth.dto.LoginRequest;

import com.example.ganzi6.domain.user.Role.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String loginId;

    private String password;

    private Role role;
}