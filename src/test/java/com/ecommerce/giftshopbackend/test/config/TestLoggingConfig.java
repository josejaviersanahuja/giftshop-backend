// Archivo: src/test/java/com/ecommerce/giftshopbackend/test/config/TestLoggingConfig.java
// ¡Esta clase proporciona TODOS los mocks necesarios para el contexto de test!

package com.ecommerce.giftshopbackend.test.config;

import com.ecommerce.giftshopbackend.domain.log.LogErrorService;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioService; // ¡Importa UsuarioService!
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration // Marca esta clase para la configuración de tests
public class TestLoggingConfig {

    @Bean // Define un bean que será un mock de LogErrorService
    public LogErrorService logErrorService() {
        return Mockito.mock(LogErrorService.class); // Creamos y retornamos el mock
    }

    @Bean // ¡Define un bean que será un mock de UsuarioService!
    public UsuarioService usuarioService() {
        return Mockito.mock(UsuarioService.class); // Creamos y retornamos el mock
    }
}