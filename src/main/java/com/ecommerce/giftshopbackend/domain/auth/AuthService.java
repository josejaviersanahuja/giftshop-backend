// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/auth/AuthService.java
package com.ecommerce.giftshopbackend.domain.auth;

// Necesitaremos el DTO de respuesta que definimos antes
// import com.ecommerce.giftshopbackend.domain.dto.auth.LoginResponseDTO; // Asegúrate de que la ruta sea correcta
import com.ecommerce.giftshopbackend.domain.exception.AuthenticationException;

public interface AuthService {

    /**
     * Autentica a un usuario con email y contraseña.
     * Genera y devuelve tokens si las credenciales son válidas.
     *
     * @param email El email del usuario.
     * @param password La contraseña del usuario.
     * @return Un objeto LoginResponseDTO con los tokens.
     * @throws AuthenticationException Si las credenciales son inválidas o el usuario no puede autenticarse.
     */
    LoginResponseDTO authenticateUserByPassword(String email, String password);

    void initiateOtpLogin(String email) throws AuthenticationException;

    LoginResponseDTO verifyOtpAndLogin(String email, String otpCode) throws AuthenticationException;

    LoginResponseDTO refreshJwtToken(String refreshToken) throws AuthenticationException;

    void logout(String tokenOrUserId); // Puedes decidir si usar el token o el ID del usuario

}