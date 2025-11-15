package com.example.ganzi6.api.auth.dto.CenterSignupRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CenterSignupRequest {

    private String loginId;

    private String password;

    private String name;

    private String address;

    private String description;
}
