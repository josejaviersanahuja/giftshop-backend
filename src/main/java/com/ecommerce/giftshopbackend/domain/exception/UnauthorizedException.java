// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/exception/UnauthorizedException.java
package com.ecommerce.giftshopbackend.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Opcional pero útil: Spring puede mapear esta excepción a un estado HTTP 401 automáticamente
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    // Opcional: Puedes añadir constructores para causa raíz
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}