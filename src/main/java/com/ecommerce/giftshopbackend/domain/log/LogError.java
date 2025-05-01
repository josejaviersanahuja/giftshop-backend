// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/log/LogError.java

package com.ecommerce.giftshopbackend.domain.log;

// Adapta los imports según tus necesidades (JPA, jOOQ, etc.)
// Si usas jOOQ, podrías mapear directamente al Record generado
// Por ahora, usamos una clase simple
import java.time.Instant;

public class LogError {
    private Integer id;
    private String tipo;
    private String severidad;
    private String mensaje;
    private String stackTrace;
    private String endpoint;
    private String metodoHttp;
    private Long usuarioId;
    private Instant fecha; // O Timestamp

    // Getters y setters (puedes generarlos con tu IDE)

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public String getMetodoHttp() { return metodoHttp; }
    public void setMetodoHttp(String metodoHttp) { this.metodoHttp = metodoHttp; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
}