package com.example.nhac.controller.Config;

import com.example.nhac.service.uerservice;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final uerservice userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new uerservice();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/add").hasAnyRole("admin")
                        .anyRequest().permitAll() // Cho phép tất cả các yêu cầu mà không cần xác thực
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("nhac") // Tên miền cho xác thực cơ bản.
                )
                .formLogin(formLogin -> formLogin.disable()); // Vô hiệu hóa form đăng nhập

        return http.build(); // Xây dựng và trả về chuỗi lọc bảo mật.
    }
}
