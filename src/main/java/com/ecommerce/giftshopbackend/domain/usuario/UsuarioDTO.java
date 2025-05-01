// Archivo: src/main/java/com/ecommerce/giftshopbackend.domain.usuario/UsuarioDTO.java
package com.ecommerce.giftshopbackend.domain.usuario;

import java.math.BigDecimal; // Para total_gastado
import java.time.Instant;    // Para timestamps (fecha_modificacion, fecha_created, ultima_compra)
import java.time.LocalDate;  // Para fechas (fecha_nacimiento)
import java.util.List;       // Para intereses

// Si necesitas manejar la ubicación como un objeto Point o similar, podrías añadir una dependencia y un tipo específico.
// Pero para empezar, latitud y longitud como Double está bien.

public class UsuarioDTO {

    private Long id;
    private String nickname;
    private String email;

    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String pais;

    private Double latitud;
    private Double longitud;

    private LocalDate fechaNacimiento;

    private Integer generoId;
    private String genero; // Ahora la vista da la descripción del género (String)
    private String estado; // Asumiendo que el estado es un String en la DB

    private List<String> intereses; // Asumiendo que esto se maneja como un List<String> o similar (ejemplo: JSONB en DB)

    private Instant ultimaCompra; // La vista da un timestamp
    private String frecuenciaCompras; // Asumiendo String basado en el nombre de la columna
    private BigDecimal totalGastado; // Para manejar moneda/decimales

    private String codigoReferencia;
    private String avatarUrl;
    private String locale;

    private Instant ultimaActualizacion; // La vista da un timestamp
    private Instant fechaRegistro;     // La vista da un timestamp

    // --- Getters y Setters para TODOS los campos ---

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getGeneroId() { return generoId; }

    public void setGeneroId(Integer generoId) { this.generoId = generoId; }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<String> intereses) {
        this.intereses = intereses;
    }

    public Instant getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(Instant ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public String getFrecuenciaCompras() {
        return frecuenciaCompras;
    }

    public void setFrecuenciaCompras(String frecuenciaCompras) {
        this.frecuenciaCompras = frecuenciaCompras;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Instant getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Instant ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}