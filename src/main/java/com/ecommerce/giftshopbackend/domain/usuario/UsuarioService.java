
// UsuarioService.java (relevante para estos endpoints)
package com.ecommerce.giftshopbackend.domain.usuario;

import com.ecommerce.giftshopbackend.infrastructure.usuario.UsuarioRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepositoryImpl usuarioRepository;

    public UsuarioService(UsuarioRepositoryImpl usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario actualizarDetalles(Long id, Usuario nuevosDetalles) {
        return usuarioRepository.actualizarDetalles(id, nuevosDetalles);
    }

    public Optional<UsuarioPublicDTO> obtenerUsuarioPublico(Long id) {
        return usuarioRepository.obtenerUsuarioPublico(id);
    }
}