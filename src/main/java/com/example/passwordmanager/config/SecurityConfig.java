package com.example.passwordmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Development-friendly security config:
     * - Permits access to the app pages, static resources and H2 console
     * - Disables CSRF only for the H2 console and for GET/POST used in dev
     * - Allows frames (needed by H2 console)
     *
     * IMPORTANT: This is for local dev only. For production, add real auth & enable
     * CSRF.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Allow H2 console frames and disable CSRF for H2 console endpoints
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/actuator/**"))
                .authorizeHttpRequests(auth -> auth
                        // public pages and static assets
                        .requestMatchers(
                                "/", "/app", "/profile", "/about",
                                "/css/**", "/js/**", "/images/**",
                                "/favicon.ico", "/h2-console/**", "/error")
                        .permitAll()
                        // allow anything else in dev
                        .anyRequest().permitAll())
                // disable login form and HTTP basic for now (we permitAll already)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        // allow frames (so H2 console works in browser)
        http.headers().frameOptions().disable();

        return http.build();
    }
}
