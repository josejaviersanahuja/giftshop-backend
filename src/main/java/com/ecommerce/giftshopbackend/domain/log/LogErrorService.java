// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/log/LogErrorService.java
// ¡Versión con inyección de interfaz!
package com.ecommerce.giftshopbackend.domain.log;

import org.springframework.stereotype.Service;

// Si definiste la interfaz LogErrorRepository, actualiza el import del GlobalExceptionHandler también
// import com.ecommerce.giftshopbackend.domain.log.LogErrorService; // Ya está bien

@Service
public class LogErrorService {

    // ¡Inyectamos la INTERFAZ! Spring se encargará de inyectar la implementación correcta (@Repository)
    private final LogErrorRepository logErrorRepository; // <-- Usamos el tipo de la interfaz y nombre estándar (minúscula)

    // Inyectamos el repositorio a través del constructor
    public LogErrorService(LogErrorRepository logErrorRepository) { // <-- Usamos el tipo de la interfaz y nombre estándar (minúscula)
        this.logErrorRepository = logErrorRepository;
    }

    public void saveLogError(LogError errorLog) {
        // La lógica de guardar ahora se delega al repositorio (a través de la interfaz)
        logErrorRepository.save(errorLog); // <-- Llamamos al método de la interfaz
        // Aquí podrías añadir lógica adicional del servicio si fuera necesario,
        // como enviar una notificación a un sistema de monitoreo, etc.
        System.out.println("Error guardado en DB via repository: " + errorLog.getMensaje()); // Mensaje de confirmación/debugging
    }
}