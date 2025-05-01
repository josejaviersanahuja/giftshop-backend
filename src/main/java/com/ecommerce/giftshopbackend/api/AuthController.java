// Archivo: src/main/java/com/ecommerce/giftshopbackend/api/AuthController.java
package com.ecommerce.giftshopbackend.api;

import com.ecommerce.giftshopbackend.domain.auth.LoginRequestDTO;
import com.ecommerce.giftshopbackend.domain.auth.LoginResponseDTO;
import com.ecommerce.giftshopbackend.domain.auth.AuthService;
// TODO: Añadir DTOs para OTP (e.g., OtpInitiateRequestDTO, OtpVerifyRequestDTO, OtpResponseDTO?)
// TODO: Añadir DTOs para Refresh Token (e.g., RefreshTokenRequestDTO)

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para login de usuario con email y contraseña.
     * Valida credenciales y genera JWT y Refresh Token.
     * POST /api/v1/auth/login
     * @param loginRequestDTO Objeto DTO con email y password.
     * @return ResponseEntity con el DTO de respuesta (tokens).
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginWithPassword(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // La lógica principal está en el servicio
        LoginResponseDTO response = authService.authenticateUserByPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        // Excepciones manejadas globalmente (401, 400, etc.)
        return ResponseEntity.ok(response);
    }

    // --- Passwordless (OTP/Email Code) Login Endpoints ---

    /**
     * Endpoint para iniciar el login sin contraseña (OTP).
     * Envía un código al email del usuario.
     * POST /api/v1/auth/otp/initiate
     // * @param otpInitiateRequestDTO DTO con el email del usuario.
     * @return ResponseEntity indicando éxito/fracaso del envío.
     */
    @PostMapping("/otp/initiate")
    public ResponseEntity<Void> initiateOtpLogin(/*@Valid @RequestBody OtpInitiateRequestDTO otpInitiateRequestDTO*/) {
        // TODO: Llamar a authService.initiateOtpLogin(email)
        System.out.println("TODO: Iniciar flujo OTP para: " /*+ otpInitiateRequestDTO.getEmail()*/);
        // Devolver 202 Accepted o 204 No Content podría ser apropiado
        return ResponseEntity.accepted().build();
    }

    /**
     * Endpoint para verificar el código OTP y completar el login.
     * Valida el código y genera JWT y Refresh Token.
     * POST /api/v1/auth/otp/verify
     // * @param otpVerifyRequestDTO DTO con el email y el código OTP.
     * @return ResponseEntity con el DTO de respuesta (tokens).
     */
    @PostMapping("/otp/verify")
    public ResponseEntity<LoginResponseDTO> verifyOtpLogin(/*@Valid @RequestBody OtpVerifyRequestDTO otpVerifyRequestDTO*/) {
        // TODO: Llamar a authService.verifyOtpAndLogin(email, otpCode)
        System.out.println("TODO: Verificar OTP para: " /*+ otpVerifyRequestDTO.getEmail()*/ + " con código: " /*+ otpVerifyRequestDTO.getCode()*/);
        // LoginResponseDTO response = authService.verifyOtpAndLogin(...);
        // return ResponseEntity.ok(response);
        // Por ahora, devolvemos un DTO vacío como placeholder
        return ResponseEntity.ok(new LoginResponseDTO("dummy_jwt_token_otp", "dummy_refresh_token_otp"));
    }

    // --- Other Auth Endpoints ---

    /**
     * Endpoint para refrescar el JWT usando un Refresh Token válido.
     * POST /api/v1/auth/refresh
     // * @param refreshTokenRequestDTO DTO con el refresh token.
     * @return ResponseEntity con el nuevo JWT (y potencialmente un nuevo refresh token).
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(/*@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO*/) {
        // TODO: Llamar a authService.refreshJwtToken(refreshToken)
        System.out.println("TODO: Refrescar token: " /*+ refreshTokenRequestDTO.getRefreshToken()*/);
        // LoginResponseDTO response = authService.refreshJwtToken(...);
        // return ResponseEntity.ok(response);
        // Placeholder
        return ResponseEntity.ok(new LoginResponseDTO("new_dummy_jwt_token", "potentially_new_dummy_refresh_token"));
    }

    /**
     * Endpoint para invalidar el token de sesión actual (logout).
     * Requiere autenticación (enviar el JWT a invalidar).
     * POST /api/v1/auth/logout
     * @return ResponseEntity indicando éxito del logout.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        // La lógica de invalidación (e.g., añadir a blacklist si usamos JWT,
        // o borrar de DB si usamos sesiones opacas) irá en el servicio.
        // Spring Security podría interceptar esto también.
        // TODO: Llamar a authService.logout(principal/token)
        System.out.println("TODO: Implementar Logout");
        return ResponseEntity.ok().build();
    }

    // --- OAuth2 Related ---
    // NOTA: Los endpoints principales de inicio (/oauth2/authorization/{providerId})
    // y callback (/login/oauth2/code/{providerId}) son manejados por Spring Security.
    // Podríamos necesitar endpoints adicionales para, por ejemplo, obtener los proveedores
    // configurados o manejar desvinculación de cuentas.

    // Ejemplo: Obtener lista de proveedores OAuth2 habilitados
    // GET /api/v1/auth/oauth2/providers
    // @GetMapping("/oauth2/providers")
    // public ResponseEntity<List<String>> getOauth2Providers() {
    //     // TODO: Lógica para obtener los providers desde la configuración
    //     return ResponseEntity.ok(List.of("google", "facebook")); // Ejemplo
    // }

}