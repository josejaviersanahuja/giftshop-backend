// Archivo: src/main/java/com/ecommerce/giftshopbackend.domain.auth/LoginResponseDTO.java
package com.ecommerce.giftshopbackend.domain.auth;

import com.ecommerce.giftshopbackend.domain.usuario.UsuarioDTO;

import java.util.UUID;

public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
    private UUID idSesionUnico;
    private UsuarioDTO usuario;

    public LoginResponseDTO(String accessToken, String refreshToken, UUID idSesionUnico, UsuarioDTO usuario) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.idSesionUnico = idSesionUnico;
        this.usuario = usuario;
    }

    public LoginResponseDTO() {}

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

    public UUID getIdSesionUnico() {
        return idSesionUnico;
    }

    public void setIdSesionUnico(UUID idSesionUnico) {
        this.idSesionUnico = idSesionUnico;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}