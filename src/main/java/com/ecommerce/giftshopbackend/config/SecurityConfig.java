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
                        .requestMatchers("/api/v1/**").hasRole("USER")
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Puedes cambiar por .oauth2Login() más adelante .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))

        return http.build();
    }
/*
    // ¡Añadimos UserDetailsService y PasswordEncoder! Son necesarios para la autenticación.
    // En producción, UserDetailsService cargar usuarios de tu base de datos.
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Creamos un usuario en memoria para tests y desarrollo inicial
        UserDetails user = User.withUsername("testuser")
                .password(passwordEncoder.encode("password")) // ¡Codificamos la contraseña!
                .roles("USER") // Le asignamos el rol USER
                .build();

        // Puedes añadir otros usuarios aquí si necesitas simular roles diferentes en tus tests
        // UserDetails admin = User.withUsername("adminuser")
        //    .password(passwordEncoder.encode("adminpass"))
        //    .roles("ADMIN", "USER")
        //    .build();

        return new InMemoryUserDetailsManager(user); //, admin);
    }

    // ¡Añadimos PasswordEncoder! Necesario para codificar contraseñas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Asegúrate de tener tu WebConfig.java para la configuración de CORS si usas .cors(withDefaults())

 */
}
