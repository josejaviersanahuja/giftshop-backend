// Archivo: src/main/java/com/ecommerce/giftshopbackend/api/AuthController.java

package com.ecommerce.giftshopbackend.api;

import com.ecommerce.giftshopbackend.domain.auth.LoginRequestDTO;
import com.ecommerce.giftshopbackend.domain.auth.LoginResponseDTO;
import com.ecommerce.giftshopbackend.domain.auth.AuthService; // Necesitaremos crear este servicio

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid; // Usaremos validación para la entrada

@RestController
@RequestMapping("/api/auth") // Un prefijo limpio para todos los endpoints de auth
public class AuthController {

    private final AuthService authService;

    // Inyección de dependencias para el servicio de autenticación
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para login de usuario con email y contraseña.
     * Valida credenciales y genera JWT y Refresh Token.
     *
     * @param loginRequestDTO Objeto DTO con email y password.
     * @return ResponseEntity con el DTO de respuesta (tokens).
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // TODO: Implementar la lógica de autenticación en AuthService
        LoginResponseDTO response = authService.authenticateUser(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // Si la autenticación falla (ej: credenciales inválidas), el servicio debería lanzar una excepción
        // que será manejada por nuestro GlobalExceptionHandler, devolviendo un 401 Unauthorized.
        // Si es exitoso, devolvemos 200 OK con los tokens.
        return ResponseEntity.ok(response);
    }

    // TODO: Añadir endpoint para refresh token
    // TODO: Añadir endpoint para logout
    // TODO: Añadir endpoint para login passwordless (OTP) - requiere estructura y lógica adicional
    // TODO: Añadir endpoint para inicio de flujo OAuth2 (Google, Facebook) - requiere configuración de Spring Security OAuth2

}