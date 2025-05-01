// Archivo: src/main/java/com/ecommerce/giftshopbackend/config/GlobalExceptionHandler.java

package com.ecommerce.giftshopbackend.config;

import com.ecommerce.giftshopbackend.domain.exception.DataIntegrityException;
import com.ecommerce.giftshopbackend.domain.exception.ResourceNotFoundException;
import com.ecommerce.giftshopbackend.domain.exception.UnauthorizedException;
import com.ecommerce.giftshopbackend.domain.log.LogErrorService;
import com.ecommerce.giftshopbackend.domain.log.LogError; // Y Entity para el log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final LogErrorService logErrorService; // Inyectamos nuestro servicio para guardar en DB

    public GlobalExceptionHandler(LogErrorService logErrorService) {
        this.logErrorService = logErrorService;
    }

    // Método auxiliar para crear el LogError
    private void createLogError(Exception ex, HttpServletRequest request, String tipo, String severidad) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));

        LogError errorLog = new LogError();
        errorLog.setTipo(tipo);
        errorLog.setSeveridad(severidad);
        // Limitar la longitud del mensaje si es muy largo para la DB (opcional, pero buena práctica)
        String message = ex.getMessage();
        errorLog.setMensaje(message != null && message.length() > 255 ? message.substring(0, 252) + "..." : message);
        errorLog.setStackTrace(sw.toString());
        errorLog.setEndpoint(request.getRequestURI());
        errorLog.setMetodoHttp(request.getMethod());
        // TODO: Implementar lógica para obtener el ID del usuario autenticado
        errorLog.setUsuarioId(null);
        errorLog.setFecha(Instant.now());

        // Guardar en la base de datos usando el servicio
        try {
            logErrorService.saveLogError(errorLog);
            logger.info("Error guardado en DB via repository: {}", errorLog.getMensaje()); // Log de confirmación
        } catch (Exception dbEx) {
            // Si falla al guardar en DB, al menos loggeamos la excepción original y la del DB
            logger.error("Failed to save error log to database. Original error: {}", ex.getMessage(), dbEx);
        }
    }

    // Este método atrapará cualquier excepción no manejada específicamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        String requestURI = servletRequest.getRequestURI();
        String method = servletRequest.getMethod();
        // Integer userId = null; // TODO: Implementar lógica para obtener el ID del usuario autenticado

        logger.error("Unhandled exception during request to {} {}", method, requestURI, ex);

        // Usamos el método auxiliar para crear y guardar el log
        createLogError(ex, servletRequest, "UNHANDLED_EXCEPTION", "ERROR"); // Tipo y severidad genéricos para no manejadas


        // Puedes devolver una respuesta más amigable al cliente
        String errorMessage = "Ocurrió un error interno. Por favor, inténtalo de nuevo más tarde.";
        // En desarrollo, quizás quieras incluir más detalles del error para debug, en prod añadir soporte idiomas

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Para manejar errores 404 (Not Found) - Cuando un recurso no existe (ej. Optional.orElseThrow())
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNotFound(NoSuchElementException ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        logger.info("Resource Not Found: {} {}", servletRequest.getMethod(), servletRequest.getRequestURI(), ex); // Nivel INFO para 404

        createLogError(ex, servletRequest, "NOT_FOUND", "INFO");

        return new ResponseEntity<>("Recurso no encontrado: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Para manejar errores 403 (Forbidden) - Generalmente lanzada por Spring Security
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        logger.warn("Access Denied: {} {}", servletRequest.getMethod(), servletRequest.getRequestURI(), ex); // Nivel WARN para 403

        createLogError(ex, servletRequest, "FORBIDDEN", "WARN");

        return new ResponseEntity<>("Acceso denegado: No tienes permisos para este recurso", HttpStatus.FORBIDDEN);
    }

    // Para manejar errores 400 (Bad Request) causados por argumentos inválidos (no validación de @Valid)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        logger.warn("Bad Request: {} {}", servletRequest.getMethod(), servletRequest.getRequestURI(), ex); // Nivel WARN para 400

        createLogError(ex, servletRequest, "BAD_REQUEST", "WARN");

        return new ResponseEntity<>("Solicitud incorrecta: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Puedes añadir handlers para tus excepciones personalizadas aquí 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        logger.info("Resource Not Found (Custom): {} {}", servletRequest.getMethod(), servletRequest.getRequestURI(), ex);
        createLogError(ex, servletRequest, "RESOURCE_NOT_FOUND", "INFO"); // Tipo específico
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // Podrías usar un mensaje más específico si quieres
    }

    // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
       HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
       logger.warn("Unauthorized Access (Custom): {} {}", servletRequest.getMethod(), servletRequest.getRequestURI(), ex);
       createLogError(ex, servletRequest, "UNAUTHORIZED", "WARN"); // Tipo específico
       return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED); // HTTP 401
    }

    //
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityException ex, WebRequest request) {
        HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
        logger.error(ex.getMessage(), ex);
        createLogError(ex, servletRequest, "DATA_INTEGRITY", "ERROR");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}