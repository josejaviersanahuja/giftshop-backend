package com.ecommerce.giftshopbackend.infrastructure.sesion;

import com.ecommerce.giftshopbackend.domain.sesion.SesionActiva;
import com.ecommerce.giftshopbackend.domain.sesion.SesionActivaInsertDTO;
import static com.ecommerce.giftshopbackend.jooq.tables.Sesionactiva.SESIONACTIVA;

import com.ecommerce.giftshopbackend.domain.sesion.SesionActivaRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SesionActivaRepositoryImpl implements SesionActivaRepository {

    private final DSLContext dsl;

    public SesionActivaRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void guardarSesion(SesionActivaInsertDTO sesion) {
        dsl.insertInto(SESIONACTIVA)
                .set(SESIONACTIVA.USUARIO_ID, sesion.getUsuarioId())
                .set(SESIONACTIVA.REFRESH_TOKEN, sesion.getRefreshToken())
                .set(SESIONACTIVA.ID_SESION_UNICO, sesion.getIdSesionUnico())
                .set(SESIONACTIVA.IP, sesion.getIp())
                .set(SESIONACTIVA.USER_AGENT, sesion.getUserAgent())
                .set(SESIONACTIVA.ORIGEN, sesion.getOrigen())
                .set(SESIONACTIVA.ES_MOVIL, sesion.getEsMovil())
                .set(SESIONACTIVA.PLATAFORMA, sesion.getPlataforma())
                .set(SESIONACTIVA.FECHA_CREACION, sesion.getFechaCreacion())
                .set(SESIONACTIVA.FECHA_EXPIRACION, sesion.getFechaExpiracion())
                .execute();
    }

    @Override
    public Optional<SesionActiva> encontrarPorIdSesionUnico(UUID idSesionUnico) {
        return dsl.selectFrom(SESIONACTIVA)
                .where(SESIONACTIVA.ID_SESION_UNICO.eq(idSesionUnico))
                .fetchOptional(record -> new SesionActiva(
                        record.getId(),
                        record.getUsuarioId(),
                        record.getRefreshToken(),
                        record.getIdSesionUnico(),
                        record.getIp(),
                        record.getUserAgent(),
                        record.getOrigen(),
                        record.getEsMovil(),
                        record.getPlataforma(),
                        record.getFechaCreacion(),
                        record.getFechaExpiracion()
                ));
    }

    // Solo para Logout from all devices
    @Override
    public void eliminarSesionPorUsuarioId(Long usuarioId) {
        dsl.deleteFrom(SESIONACTIVA)
                .where(SESIONACTIVA.USUARIO_ID.eq(usuarioId))
                .execute();
    }


    @Override
    public void eliminarSesionPorIdSesionUnico(UUID idSesionUnico) {
        dsl.deleteFrom(SESIONACTIVA)
                .where(SESIONACTIVA.ID_SESION_UNICO.eq(idSesionUnico))
                .execute();
    }
}
