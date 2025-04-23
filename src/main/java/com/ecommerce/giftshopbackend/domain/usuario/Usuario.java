package com.ecommerce.giftshopbackend.domain.usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Usuario {
    private Long id;
    private String nickname;
    private String email;
    private String passwordHash;
    private Integer referidoPor;
    private LocalDateTime fechaCreated;
    private LocalDateTime fechaModificacion;

    // Detalles
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String pais;
    private double latitud;
    private double longitud;
    private String codigoReferencia;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModificacionDetalle;
    private LocalDate fechaNacimiento;
    private Integer generoId;
    private String estado;
    private String avatarUrl;
    private String locale;
    private List<String> intereses;
    private String canalRegistro;
    private String dispositivoRegistro;
    private LocalDateTime ultimaCompra;
    private int frecuenciaCompras;
    private BigDecimal totalGastado;

    // Getters y setters

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

    public Integer getReferidoPor() {
        return referidoPor;
    }

    public void setReferidoPor(Integer referidoPor) {
        this.referidoPor = referidoPor;
    }

    public LocalDateTime getFechaCreated() {
        return fechaCreated;
    }

    public void setFechaCreated(LocalDateTime fechaCreated) {
        this.fechaCreated = fechaCreated;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaModificacionDetalle() {
        return fechaModificacionDetalle;
    }

    public void setFechaModificacionDetalle(LocalDateTime fechaModificacionDetalle) {
        this.fechaModificacionDetalle = fechaModificacionDetalle;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public List<String> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<String> intereses) {
        this.intereses = intereses;
    }

    public String getCanalRegistro() {
        return canalRegistro;
    }

    public void setCanalRegistro(String canalRegistro) {
        this.canalRegistro = canalRegistro;
    }

    public String getDispositivoRegistro() {
        return dispositivoRegistro;
    }

    public void setDispositivoRegistro(String dispositivoRegistro) {
        this.dispositivoRegistro = dispositivoRegistro;
    }

    public LocalDateTime getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(LocalDateTime ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public int getFrecuenciaCompras() {
        return frecuenciaCompras;
    }

    public void setFrecuenciaCompras(int frecuenciaCompras) {
        this.frecuenciaCompras = frecuenciaCompras;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }
}
