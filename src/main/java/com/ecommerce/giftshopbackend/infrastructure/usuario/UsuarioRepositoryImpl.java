// infrastructure/usuario/UsuarioRepositoryImpl.java
package com.ecommerce.giftshopbackend.infrastructure.usuario;

import java.util.Objects;
import java.util.Optional;
import java.time.LocalDateTime;
import static org.jooq.impl.DSL.field;
import static com.ecommerce.giftshopbackend.jooq.tables.UsuarioDetalle.USUARIO_DETALLE;
import static com.ecommerce.giftshopbackend.jooq.tables.Usuario.USUARIO;
import static com.ecommerce.giftshopbackend.jooq.tables.Genero.GENERO;

import com.ecommerce.giftshopbackend.domain.exception.ResourceNotFoundException;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioCredencialesDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioPublicDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioRepository;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import static org.jooq.impl.DSL.*;

// SRC/infrastructure/usuario/UsuarioRepositoryImpl.java -> SRC/domain/usuario/Usuario.java, SRC/domain/usuario/UsuarioPublicDTO.java, SRC/domain/usuario/UsuarioRepository.java

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final DSLContext dsl;

    public UsuarioRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public UsuarioDTO actualizarDetallesUsuario(Long id, UsuarioDTO nuevosDetalles) {

        try {
            String currentNickname = dsl.select(USUARIO.NICKNAME)
                    .from(USUARIO)
                    .where(USUARIO.ID.eq(id.intValue()))
                    .fetchOptional(USUARIO.NICKNAME)
                    .orElse(null);

            if (currentNickname == null) {
                throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
            }

            // --- 1. Lógica específica para NICKNAME (Tabla USUARIO) ---
            String nuevoNickname = nuevosDetalles.getNickname();

            if (!Objects.equals(nuevoNickname, currentNickname) && nuevoNickname != null) {
                dsl.update(USUARIO)
                        .set(USUARIO.NICKNAME, nuevoNickname)
                        .where(USUARIO.ID.eq(id.intValue()))
                        .execute();
            }

            // --- PREPARAR VALOR DE UBICACIÓN ---
            // Obtener el tipo de dato del campo objetivo
            DataType<?> ubicacionDataType = USUARIO_DETALLE.UBICACION.getDataType();
            // Declarar una variable para la expresión de campo (usamos comodín <?>)
            Field<?> ubicacionValueExpression;
            if (nuevosDetalles.getLatitud() != null && nuevosDetalles.getLongitud() != null) {
                // Si hay lat/lon, crear la expresión field()
                ubicacionValueExpression = field(
                        "ST_SetSRID(ST_MakePoint({0}, {1}), 4326)",
                        ubicacionDataType, // Usar el DataType obtenido
                        val(nuevosDetalles.getLongitud()),
                        val(nuevosDetalles.getLatitud())
                );

                dsl.update(USUARIO_DETALLE)
                        // Mapeo de lat/lon a Point usando PostGI
                        .set(USUARIO_DETALLE.UBICACION, ubicacionValueExpression)
                        .where(USUARIO_DETALLE.ID.eq(id.intValue())) // SIN .intValue()
                        .execute();
            } else {
                // Si no hay lat/lon, crear una expresión de valor NULL explícito
                ubicacionValueExpression = val(null, ubicacionDataType);
            }

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
                    .where(USUARIO_DETALLE.ID.eq(id.intValue()))
                    .execute();

            return nuevosDetalles;
        } catch (DataAccessException e) {
            throw new DataAccessException("¡ERROR de base de datos al actualizar usuario " + id + "! Causa: " + e.getMessage());
        }
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

    // findByEmail
    @Override
    public Optional<UsuarioDTO> findByEmail(String email) {
        // Importaciones estáticas para las tablas generadas por jOOQ (Usuario, UsuarioDetalle, Genero)
        // Importación estática para funciones DSL (field, lateral etc.)

        return Optional.ofNullable(
                dsl.select(
                                // Seleccionar y aliar campos para que coincidan con UsuarioDTO (camelCase)
                                USUARIO.ID.as("id"),
                                USUARIO.NICKNAME.as("nickname"),
                                USUARIO.EMAIL.as("email"),

                                USUARIO_DETALLE.NOMBRE.as("nombre"),
                                USUARIO_DETALLE.APELLIDO.as("apellido"),
                                USUARIO_DETALLE.DIRECCION.as("direccion"),
                                USUARIO_DETALLE.TELEFONO.as("telefono"),
                                USUARIO_DETALLE.PAIS.as("pais"),

                                // Mapeo de ubicacion (Point) a latitud y longitud
                                // Necesita la función ST_Y y ST_X de PostGIS y pasar a geometry
                                field("ST_Y({0}::geometry)", Double.class, USUARIO_DETALLE.UBICACION).as("latitud"),
                                field("ST_X({0}::geometry)", Double.class, USUARIO_DETALLE.UBICACION).as("longitud"),

                                USUARIO_DETALLE.FECHA_NACIMIENTO.as("fechaNacimiento"),

                                // Mapeo del género. Asumiendo que Genero.DESCRIPCION es un String y el JOIN es suficiente.
                                // Si Genero.DESCRIPCION es JSONB y necesitas la traducción dinámica, la consulta es más compleja
                                // y podría requerir usar `lateral` o funciones DSL más avanzadas para el `->`.
                                // Por simplicidad, seleccionamos la descripción directa. Si necesitas la traducción, ajusta aquí.
                                GENERO.DESCRIPCION.as("genero"),

                                USUARIO_DETALLE.ESTADO.as("estado"),

                                // Mapeo de Intereses. Asumiendo que en la DB es texto plano (VARCHAR, TEXT)
                                // Si es JSONB o array, jOOQ tiene mapeos o bindings personalizados.
                                // Seleccionamos como String y asumimos que jOOQ o un binding lo mapeará a List<String> si está configurado.
                                // Si no, necesitarás lógica manual o un binding específico.
                                USUARIO_DETALLE.INTERESES.as("intereses"),


                                USUARIO_DETALLE.ULTIMA_COMPRA.as("ultimaCompra"),
                                USUARIO_DETALLE.FRECUENCIA_COMPRAS.as("frecuenciaCompras"),
                                USUARIO_DETALLE.TOTAL_GASTADO.as("totalGastado"), // jOOQ debería mapear NUMERIC/DECIMAL a BigDecimal

                                USUARIO_DETALLE.CODIGO_REFERENCIA.as("codigoReferencia"),
                                USUARIO_DETALLE.AVATAR_URL.as("avatarUrl"),
                                USUARIO_DETALLE.LOCALE.as("locale"),

                                USUARIO_DETALLE.FECHA_MODIFICACION.as("ultimaActualizacion"),
                                USUARIO.FECHA_CREATED.as("fechaRegistro") // Asumiendo que u.fecha_created existe y es el campo de creación

                        )
                        .from(USUARIO) // Desde la tabla principal Usuario
                        .join(USUARIO_DETALLE).on(USUARIO.ID.eq(USUARIO_DETALLE.ID)) // JOIN con Usuario_Detalle por ID
                        .leftJoin(GENERO).on(USUARIO_DETALLE.GENERO_ID.eq(GENERO.ID)) // LEFT JOIN con Genero

                        .where(USUARIO.EMAIL.eq(email)) // Filtrar por email

                        .fetchOneInto(UsuarioDTO.class) // Obtener un resultado y mapearlo a UsuarioDTO
        );
    }

    // --- IMPLEMENTACIÓN DEL NUEVO MÉTODO ---
    @Override
    public Optional<UsuarioCredencialesDTO> findCredentialsByEmail(String email) {
        // Seleccionamos los campos de la tabla Usuario que coinciden con UsuarioCredencialesDTO
        return dsl.select(
                        USUARIO.ID,                 // Mapeará a id (Long/Integer)
                        USUARIO.NICKNAME,           // Mapeará a nickname (String)
                        USUARIO.EMAIL,              // Mapeará a email (String)
                        USUARIO.PASSWORD_HASH,      // Mapeará a passwordHash (String)
                        USUARIO.FECHA_CREATED,      // Mapeará a fechaCreated (LocalDateTime)
                        USUARIO.REFERIDO_POR,       // Mapeará a referidoPor (Integer)
                        USUARIO.FECHA_MODIFICACION  // Mapeará a fechaModificacion (LocalDateTime)
                )
                .from(USUARIO)
                .where(USUARIO.EMAIL.eq(email))
                .fetchOptionalInto(UsuarioCredencialesDTO.class);
    }
}
