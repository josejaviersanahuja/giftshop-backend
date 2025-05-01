// Archivo: src/main/java/com/ecommerce/giftshopbackend/api/UsuarioControllerTest.java
// ¡Versión corregida para inyectar el mock de LogErrorService en el contexto del test!

package com.ecommerce.giftshopbackend.api;

import com.ecommerce.giftshopbackend.domain.log.LogError; // ¡Importa LogError!
import com.ecommerce.giftshopbackend.domain.log.LogErrorService; // ¡Importa LogErrorService!
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioDTO;
import org.mockito.ArgumentCaptor; // ¡Importa ArgumentCaptor!

import com.ecommerce.giftshopbackend.domain.usuario.UsuarioPublicDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioService;
import com.ecommerce.giftshopbackend.test.config.TestLoggingConfig; // Importa la configuración de test
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// ¡Quitamos la importación de MockBean!
// import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import; // Importa @Import
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

// Usamos @Import para incluir la configuración de nuestros mocks (ambos servicios)
@Import(TestLoggingConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ¡Ya NO usamos @MockBean aquí! El mock lo provee TestLoggingConfig
    // Podemos inyectar el mock si lo necesitamos para interactuar con él en el test:
    @Autowired
    private UsuarioService usuarioService; // Este será el mock de UsuarioService

    // También puedes inyectar el mock de LogErrorService si lo necesitas para verificar llamadas:
    @Autowired
    private LogErrorService logErrorService; // Este será el mock de LogErrorService


    @Test
    @DisplayName("PUT /api/v1/users/{id}/details - devuelve 200 OK")
    void actualizarDetalles_devuelve200() throws Exception {
        UsuarioDTO mockUsuario = new UsuarioDTO();
        mockUsuario.setId(1L);

        // Mocking the service method call
        // ¡Usamos el 'usuarioService' inyectado, que ahora es el mock de TestLoggingConfig!
        Mockito.when(usuarioService.actualizarDetalles(Mockito.eq(1L), Mockito.any(UsuarioDTO.class)))
                .thenReturn(mockUsuario);

        mockMvc.perform(put("/api/v1/users/1/details")
                        .with(user("testuser").roles("USER")) // <-- ¡Aquí!
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUsuario)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/users/public/{id} - devuelve 200 OK")
    void obtenerUsuarioPublico_devuelve200() throws Exception {
        // Instantiate the record with required arguments
        UsuarioPublicDTO dto = new UsuarioPublicDTO(
                1L,
                "testuser",
                "Spain",
                "https://example.com/avatar.png",
                "es"
        );
        // Mocking the service method call
        // ¡Usamos el 'usuarioService' inyectado!
        Mockito.when(usuarioService.obtenerUsuarioPublico(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/v1/users/public/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/users/public/{id} - devuelve 404 si no existe")
    void obtenerUsuarioPublico_devuelve404() throws Exception {
        // No need to instantiate UsuarioPublicDTO here as the mock returns Optional.empty()
        // ¡Usamos el 'usuarioService' inyectado!
        Mockito.when(usuarioService.obtenerUsuarioPublico(0L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/public/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id}/details - devuelve 400 con JSON inválido")
    void actualizarDetalles_devuelve400_conJsonInvalido() throws Exception {
        String jsonInvalido = "{\"nombreNoValido\": \"test\"}";

        // Note: This test relies on Spring's default JSON deserialization handling.
        // If your controller has a specific validation setup, the behavior might differ.
        mockMvc.perform(put("/api/v1/users/1/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/users/cause-error - GlobalExceptionHandler debe capturar y loggear")
    void causeErrorEndpoint_globalExceptionHandlerShouldCatchAndLog() throws Exception {
        // Realizamos una petición al endpoint que sabemos que lanza una excepción
        // Nota: Si este endpoint de test también está protegido por seguridad,
        // necesitarás añadir `.with(user("testuser").roles("USER"))` aquí también.
        mockMvc.perform(get("/api/v1/users/cause-error"))
                .andExpect(status().isInternalServerError()); // Verificamos que el manejador devuelve 500

        // Verificamos que el método saveLogError del mock fue llamado EXACTAMENTE una vez
        Mockito.verify(logErrorService, Mockito.times(1)).saveLogError(Mockito.any(LogError.class));

        // Opcional: Capturar el objeto LogError que se pasó para verificar su contenido
        ArgumentCaptor<LogError> logErrorCaptor = ArgumentCaptor.forClass(LogError.class);
        Mockito.verify(logErrorService).saveLogError(logErrorCaptor.capture());

        LogError capturedLogError = logErrorCaptor.getValue();

        // ¡Ahora puedes hacer aserciones sobre los datos capturados en el LogError!
        // Por ejemplo:
        assertEquals("EXCEPTION", capturedLogError.getTipo()); // Asumiendo que mapeas a este tipo genérico
        assertEquals("ERROR", capturedLogError.getSeveridad()); // Asumiendo esta severidad por defecto
        // assertTrue(capturedLogError.getMensaje().contains("Este es un error de prueba")); // Verifica parte del mensaje
        // assertEquals("/api/v1/users/cause-error", capturedLogError.getEndpoint()); // Verifica el endpoint
        // assertEquals("GET", capturedLogError.getMetodoHttp()); // Verifica el método HTTP
        // assertNotNull(capturedLogError.getStackTrace()); // Verifica que el stack trace no es nulo
        // Puedes verificar usuarioId si tu GlobalExceptionHandler lo obtiene
        // Puedes verificar fecha si es relevante
    }
}