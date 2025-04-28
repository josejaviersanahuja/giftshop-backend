// infrastructure/usuario/UsuarioRepositoryImpl.java
package com.ecommerce.giftshopbackend.infrastructure.usuario;

import java.util.Optional;
import java.time.LocalDateTime;
import static org.jooq.impl.DSL.field;
import static com.ecommerce.giftshopbackend.jooq.tables.UsuarioDetalle.USUARIO_DETALLE;

import com.ecommerce.giftshopbackend.domain.usuario.Usuario;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioPublicDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

// SRC/infrastructure/usuario/UsuarioRepositoryImpl.java -> SRC/domain/usuario/Usuario.java, SRC/domain/usuario/UsuarioPublicDTO.java, SRC/domain/usuario/UsuarioRepository.java

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final DSLContext dsl;

    public UsuarioRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Usuario actualizarDetalles(Long id, Usuario nuevosDetalles) {
        dsl.update(USUARIO_DETALLE)
                .set(USUARIO_DETALLE.NOMBRE, nuevosDetalles.getNombre())
                .set(USUARIO_DETALLE.APELLIDO, nuevosDetalles.getApellido())
                .set(USUARIO_DETALLE.DIRECCION, nuevosDetalles.getDireccion())
                .set(USUARIO_DETALLE.TELEFONO, nuevosDetalles.getTelefono())
                .set(USUARIO_DETALLE.PAIS, nuevosDetalles.getPais())
                .set(USUARIO_DETALLE.FECHA_NACIMIENTO, nuevosDetalles.getFechaNacimiento())
                .set(USUARIO_DETALLE.GENERO_ID, nuevosDetalles.getGeneroId())
                .set(USUARIO_DETALLE.AVATAR_URL, nuevosDetalles.getAvatarUrl())
                .set(USUARIO_DETALLE.LOCALE, nuevosDetalles.getLocale())
                .set(USUARIO_DETALLE.INTERESES,
                        nuevosDetalles.getIntereses() != null ? String.join(",", nuevosDetalles.getIntereses()) : null)
                .set(USUARIO_DETALLE.FECHA_MODIFICACION, LocalDateTime.now())
                .where(USUARIO_DETALLE.ID.eq(id.intValue()))
                .execute();

        return nuevosDetalles;
    }

    @Override
    public Optional<UsuarioPublicDTO> obtenerUsuarioPublico(Long id) {
        return Optional.ofNullable(dsl.select(
                        field("id", Long.class),
                        field("nickname", String.class),
                        field("pais", String.class),
                        field("avatar_url", String.class),
                        field("locale", String.class)
                )
                .from("vista_usuario_publico")
                .where(field("id").eq(id))
                .fetchOneInto(UsuarioPublicDTO.class));
    }
}