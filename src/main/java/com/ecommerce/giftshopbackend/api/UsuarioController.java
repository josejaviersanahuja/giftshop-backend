// UsuarioController.java
package com.ecommerce.giftshopbackend.api;

import com.ecommerce.giftshopbackend.domain.usuario.Usuario;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioPublicDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<Usuario> actualizarDetalles(@PathVariable Long id, @RequestBody Usuario detallesActualizados) {
        return ResponseEntity.ok(usuarioService.actualizarDetalles(id, detallesActualizados));
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<UsuarioPublicDTO> obtenerUsuarioPublico(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPublico(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}