package com.ecommerce.giftshopbackend.domain.usuario;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO para transportar datos de credenciales y estado básico del usuario,
 * principalmente para uso interno en la lógica de autenticación.
 * Refleja los campos clave de la tabla 'Usuario'.
 */
public class UsuarioCredencialesDTO {

    private Long id; // Usamos Long por escalabilidad (corresponde a SERIAL/BIG SERIAL/BIGINT)
    private String nickname;
    private String email;
    private String passwordHash; // Puede ser null si usan otros métodos de login
    private LocalDateTime fechaCreated;
    private Long referidoPor; // ID del usuario que lo refirió (puede ser null)
    private LocalDateTime fechaModificacion;

    // Constructor sin argumentos (útil para frameworks)
    public UsuarioCredencialesDTO() {
    }

    // Constructor con todos los argumentos (útil para creación directa)
    public UsuarioCredencialesDTO(Long id, String nickname, String email, String passwordHash, LocalDateTime fechaCreated, Long referidoPor, LocalDateTime fechaModificacion) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fechaCreated = fechaCreated;
        this.referidoPor = referidoPor;
        this.fechaModificacion = fechaModificacion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getFechaCreated() {
        return fechaCreated;
    }

    public void setFechaCreated(LocalDateTime fechaCreated) {
        this.fechaCreated = fechaCreated;
    }

    public Long getReferidoPor() {
        return referidoPor;
    }

    public void setReferidoPor(Long referidoPor) {
        this.referidoPor = referidoPor;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    // equals y hashCode (basados en id si no es null, o email/nickname si id es null)
    // Importante para comparar DTOs si los usas en colecciones o tests.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioCredencialesDTO that = (UsuarioCredencialesDTO) o;
        // Prioriza ID si ambos lo tienen
        if (id != null && that.id != null) {
            return id.equals(that.id);
        }
        // Si los IDs son null, usa email (que es UNIQUE)
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        // Prioriza ID si no es null, si no, usa email
        return Objects.hash(id != null ? id : email);
    }

    // toString (útil para logging/debug)
    @Override
    public String toString() {
        return "UsuarioCredencialesDTO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='[PROTEGIDO]'" + // No mostrar hash en logs
                ", fechaCreated=" + fechaCreated +
                ", referidoPor=" + referidoPor +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}