package com.example.busreservation.security;


import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
        // @Autowired
        // private JWTEntryPoint jwtEntryPoint;

        // @Autowired
        // private JwtAuthFilter jwtAuthFilter;

        @Bean
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(authReq -> authReq
                                                .anyRequest().permitAll() // ✅ allow every request
                                )
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .httpBasic(Customizer.withDefaults());

                // No need for jwtAuthFilter if you're permitting everything
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordENcoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

}
