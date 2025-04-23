package com.ecommerce.giftshopbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Activa CORS (debes tener WebConfig también)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/**").permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Puedes cambiar por .oauth2Login() más adelante .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))

        return http.build();
    }
}
