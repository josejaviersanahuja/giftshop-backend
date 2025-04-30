// Archivo: src/main/java/com/ecommerce/giftshopbackend.domain.auth/LoginResponseDTO.java
package com.ecommerce.giftshopbackend.domain.auth;

public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
    // Puedes añadir más cosas si necesitas (ie: roles del usuario)

    public LoginResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters y setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}