// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/auth/AuthService.java
package com.ecommerce.giftshopbackend.domain.auth;

// Necesitaremos el DTO de respuesta que definimos antes
// import com.ecommerce.giftshopbackend.domain.dto.auth.LoginResponseDTO; // Asegúrate de que la ruta sea correcta

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
    LoginResponseDTO authenticateUser(String email, String password);

    // TODO: Añadir métodos para refrescar tokens, logout, etc.

}