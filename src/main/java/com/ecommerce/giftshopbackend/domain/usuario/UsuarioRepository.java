package com.ecommerce.giftshopbackend.domain.usuario;

import java.util.Optional;

/**
 * Interfaz del repositorio para la entidad Usuario.
 * Define las operaciones de acceso a datos para los usuarios.
 */
public interface UsuarioRepository {

    /**
     * Actualiza los detalles (no sensibles) de un usuario.
     * La implementación debe manejar la actualización en las tablas correspondientes (USUARIO, USUARIO_DETALLE).
     *
     * @param id El ID del usuario a actualizar (Long o UUID).
     * @param nuevosDetalles DTO con la información a actualizar.
     * @return El UsuarioDTO con el estado final después de la actualización.
     * @throws ResourceNotFoundException Si el usuario no se encuentra.
     * @throws DataAccessException Si ocurre un error de base de datos.
     */
    UsuarioDTO actualizarDetallesUsuario(Long id, UsuarioDTO nuevosDetalles); // Renombrado para claridad

    /**
     * Obtiene la información pública de un usuario por su ID.
     * Usado para mostrar perfiles a otros usuarios, etc.
     *
     * @param id El ID del usuario (Long o UUID).
     * @return Un Optional conteniendo el UsuarioPublicDTO si se encuentra, o vacío si no.
     */
    Optional<UsuarioPublicDTO> obtenerUsuarioPublico(Long id); // Asumiendo que el ID es Long aquí también

    /**
     * Busca un usuario por su email y devuelve su información pública/general.
     * Útil para mostrar información del usuario en la UI, pero NO para autenticación.
     *
     * @param email El email del usuario.
     * @return Un Optional conteniendo el UsuarioDTO si se encuentra, o vacío si no.
     */
    Optional<UsuarioDTO> findByEmail(String email);

    /**
     * Busca un usuario por su email y devuelve sus credenciales y datos básicos.
     * **Este es el método a usar para la autenticación por contraseña**, ya que incluye el password_hash.
     *
     * @param email El email del usuario a autenticar.
     * @return Un Optional conteniendo el UsuarioCredencialesDTO si se encuentra, o vacío si no.
     */
    Optional<UsuarioCredencialesDTO> findCredentialsByEmail(String email); // ¡NUEVO MÉTODO!

    // --- Otros métodos posibles que podrías necesitar ---
    // Optional<UsuarioDTO> findById(Long id); // Para obtener el DTO completo por ID
    // boolean existsByEmail(String email);
    // boolean existsByNickname(String nickname);
    // void save(Usuario usuario); // Para crear nuevos usuarios
    // ...etc.
}