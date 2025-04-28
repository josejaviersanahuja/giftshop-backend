// Archivo: src/main/java/com/ecommerce/giftshopbackend/config/GlobalExceptionHandler.java

package com.ecommerce.giftshopbackend.config;

import com.ecommerce.giftshopbackend.domain.log.LogErrorService;
import com.ecommerce.giftshopbackend.domain.log.LogError; // Y Entity para el log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final LogErrorService logErrorService; // Inyectamos nuestro servicio para guardar en DB

    public GlobalExceptionHandler(LogErrorService logErrorService) {
        this.logErrorService = logErrorService;
    }

    // Este método atrapará cualquier excepción no manejada específicamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String requestURI = servletRequest.getRequestURI();
        String method = servletRequest.getMethod();
        // Aquí podrías intentar obtener el ID del usuario autenticado si es relevante
        // Por ahora, lo dejamos como null o un valor indicativo
        Integer userId = null; // TODO: Implementar lógica para obtener el ID del usuario autenticado

        logger.error("Unhandled exception during request to {} {}", method, requestURI, ex);

        // Formatear el stack trace para guardarlo en la DB
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        // Crear el objeto LogError para guardar en la DB
        LogError errorLog = new LogError();
        errorLog.setTipo("EXCEPTION"); // O un tipo más específico si puedes determinarlo
        errorLog.setSeveridad("ERROR");
        errorLog.setMensaje(ex.getMessage());
        errorLog.setStackTrace(stackTrace);
        errorLog.setEndpoint(requestURI);
        errorLog.setMetodoHttp(method);
        errorLog.setUsuarioId(userId);
        errorLog.setFecha(Instant.now());

        // Guardar en la base de datos usando el servicio
        try {
            logErrorService.saveLogError(errorLog);
        } catch (Exception dbEx) {
            // Si falla al guardar en DB, al menos loggeamos la excepción original y la del DB
            logger.error("Failed to save error log to database", dbEx);
        }

        // Puedes devolver una respuesta más amigable al cliente
        String errorMessage = "Ocurrió un error interno. Por favor, inténtalo de nuevo más tarde.";
        // En desarrollo, quizás quieras incluir más detalles del error para debug
        // if (environment.acceptsProfiles(Profiles.of("dev"))) {
        //     errorMessage = ex.getMessage();
        // }

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Puedes añadir más métodos @ExceptionHandler para tipos específicos de excepciones
    // Por ejemplo, para validaciones, acceso denegado, etc.
    // @ExceptionHandler(AccessDeniedException.class)
    // public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
    //     // ... lógica similar, quizás con diferente severidad o tipo
    //     return new ResponseEntity<>("Acceso denegado", HttpStatus.FORBIDDEN);
    // }
}