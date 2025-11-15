package com.example.ganzi6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)      // CSRF 비활성화 (API 용)
                .formLogin(AbstractHttpConfigurer::disable) // 로그인 폼 안씀
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 로그인도 안씀
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()           // 일단 전체 오픈
                );

        return http.build();
    }
}