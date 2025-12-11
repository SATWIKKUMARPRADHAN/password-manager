package com.example.passwordmanager.config;

import com.example.passwordmanager.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/h2-console/**", "/actuator/**", "/api/**"))
                                .authorizeHttpRequests(auth -> auth
                                                // public pages and static assets
                                                .requestMatchers(
                                                                "/", "/landing", "/login", "/signup", "/about",
                                                                "/css/**", "/js/**", "/images/**",
                                                                "/favicon.ico", "/h2-console/**", "/error")
                                                .permitAll()
                                                // everything API and app requires authentication
                                                .requestMatchers("/app", "/profile", "/api/**", "/export", "/add",
                                                                "/delete/**", "/edit/**")
                                                .authenticated()
                                                .anyRequest().permitAll())
                                .userDetailsService(userDetailsService)
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/app", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                                .logoutSuccessUrl("/")
                                                .permitAll())
                                .httpBasic(httpBasic -> httpBasic.disable());

                // allow frames (so H2 console works in browser)
                http.headers().frameOptions().disable();

                return http.build();
        }
}
