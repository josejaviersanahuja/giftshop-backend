
// UsuarioService.java (relevante para estos endpoints)
package com.ecommerce.giftshopbackend.domain.usuario;

import org.springframework.stereotype.Service;

import java.util.Optional;

// SRC/domain/usuario/UsuarioService.java -> SRC/infrastructure/usuario/UsuarioRepositoryImpl.java

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO actualizarDetalles(Long id, UsuarioDTO nuevosDetalles) {
        return usuarioRepository.actualizarDetallesUsuario(id, nuevosDetalles);
    }

    public Optional<UsuarioPublicDTO> obtenerUsuarioPublico(Long id) {
        return usuarioRepository.obtenerUsuarioPublico(id);
    }
}