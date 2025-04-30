// Archivo: src/main/java/com/ecommerce/giftshopbackend.domain.exception/AuthenticationException.java
package com.ecommerce.giftshopbackend.domain.exception;

// Puedes ponerle un ResponseStatus o no, depende de si quieres un 401 genérico o manejarlo en el GlobalExceptionHandler
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ResponseStatus;
// @ResponseStatus(HttpStatus.UNAUTHORIZED) // Ejemplo: si quieres 401 por defecto

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}