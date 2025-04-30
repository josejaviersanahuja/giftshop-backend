// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/exception/ResourceNotFoundException.java
package com.ecommerce.giftshopbackend.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Opcional pero útil: Spring puede mapear esta excepción a un estado HTTP 404 automáticamente
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Opcional: Puedes añadir constructores para causa raíz
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}