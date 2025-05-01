package com.ecommerce.giftshopbackend.domain.sesion;

import java.util.Optional;
import java.util.UUID;

public interface SesionActivaRepository {
    void guardarSesion(SesionActivaInsertDTO sesion);
    Optional<SesionActiva> encontrarPorIdSesionUnico(UUID idSesionUnico);
    void eliminarSesionPorUsuarioId(Long usuarioId);
    void eliminarSesionPorIdSesionUnico(UUID idSesionUnico);
}
