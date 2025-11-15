package com.example.ganzi6.api.auth.dto.MarketSignupRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketSignupRequest {

    private String loginId;

    private String password;

    private String name;

    private String address;

    private String description;
}