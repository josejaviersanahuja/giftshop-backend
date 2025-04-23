package com.ecommerce.giftshopbackend.api;

import com.ecommerce.giftshopbackend.domain.usuario.Usuario;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioPublicDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Deprecated in Spring Boot 3.4+
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // @MockBean is deprecated in Spring Boot 3.4.x, but still functional for now.
    // The compilation error "Expected 5 arguments but found 0" is unrelated to this warning.
    @MockBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("PUT /api/v1/users/{id}/details - devuelve 200 OK")
    void actualizarDetalles_devuelve200() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1L);

        // Mocking the service method call
        Mockito.when(usuarioService.actualizarDetalles(Mockito.eq(1L), Mockito.any(Usuario.class)))
                .thenReturn(mockUsuario);

        mockMvc.perform(put("/api/v1/users/1/details")
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
                "http://example.com/avatar.png",
                "es"
        );
        // Mocking the service method call
        Mockito.when(usuarioService.obtenerUsuarioPublico(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/v1/users/public/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/users/public/{id} - devuelve 404 si no existe")
    void obtenerUsuarioPublico_devuelve404() throws Exception {
        // No need to instantiate UsuarioPublicDTO here as the mock returns Optional.empty()
        Mockito.when(usuarioService.obtenerUsuarioPublico(0L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/public/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id}/details - devuelve 400 con JSON inválido")
    void actualizarDetalles_devuelve400_conJsonInvalido() throws Exception {
        String jsonInvalido = "{\"nombreNoValido\": \"test\"}";

        // Note: This test relies on Spring's default JSON deserialization handling.
        // If your controller has specific validation setup, the behavior might differ.
        mockMvc.perform(put("/api/v1/users/1/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}
