package com.ecommerce.giftshopbackend.domain.usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario actualizarDetalles(Long id, Usuario nuevosDetalles);
    Optional<UsuarioPublicDTO> obtenerUsuarioPublico(Long id);
}
