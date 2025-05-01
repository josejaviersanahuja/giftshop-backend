package com.ecommerce.giftshopbackend.domain.sesion;

import java.time.LocalDateTime;
import java.util.UUID;

public class SesionActivaInsertDTO {

    private Long usuarioId;
    private String refreshToken;
    private UUID idSesionUnico;
    private String ip;
    private String userAgent;
    private String origen;
    private Boolean esMovil;
    private String plataforma;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaExpiracion;

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public SesionActivaInsertDTO() {}

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Boolean getEsMovil() {
        return esMovil;
    }

    public void setEsMovil(Boolean esMovil) {
        this.esMovil = esMovil;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
