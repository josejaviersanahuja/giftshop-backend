// UsuarioController.java
package com.ecommerce.giftshopbackend.api;

import com.ecommerce.giftshopbackend.domain.usuario.UsuarioDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioPublicDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioService;
import com.ecommerce.giftshopbackend.util.RequestMetadata;
import com.ecommerce.giftshopbackend.util.RequestMetadataExtractor;
import com.ecommerce.giftshopbackend.util.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// SRC/api/UsuarioController.java -> SRC/domain/usuario/Usuario.java, SRC/domain/usuario/UsuarioPublicDTO.java, SRC/domain/usuario/UsuarioService.java

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("")
    public ResponseEntity<UsuarioDTO> createUsuarioWithPassword(
            String email, String password, String nickname, String codigoReferencia, HttpServletRequest request
    ) {
        RequestMetadata requestMetadata = RequestMetadataExtractor.extract(request);

        // UsuarioDTO creado = usuarioService.crearUsuario(usuarioDTO, requestMetadata);
        return ResponseEntity.status(HttpStatus.CREATED).body({});
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<UsuarioDTO> actualizarDetalles(@PathVariable Long id, @RequestBody UsuarioDTO detallesActualizados) {
        return ResponseEntity.ok(usuarioService.actualizarDetalles(id, detallesActualizados));
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<UsuarioPublicDTO> obtenerUsuarioPublico(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPublico(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ¡Endpoint temporal para probar el manejador de errores global!
    @GetMapping("/cause-error") // Puedes elegir cualquier path
    public ResponseEntity<String> causeErrorEndpoint() {
        // ¡Lanzamos una excepción a propósito!
        throw new RuntimeException("¡Este es un error de prueba para GlobalExceptionHandler!");
        // El manejador de errores global debería capturar esto
        // return ResponseEntity.ok("Si ves esto, el manejador de errores no funcionó..."); // Esta línea no se alcanzará
    }
}