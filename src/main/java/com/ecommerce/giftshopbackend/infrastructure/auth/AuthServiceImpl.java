// Archivo: src/main/java/com/ecommerce/giftshopbackend/infrastructure/auth/AuthServiceImpl.java

package com.ecommerce.giftshopbackend.infrastructure.auth; // ¡Cambiamos el paquete aquí!

import com.ecommerce.giftshopbackend.domain.auth.AuthService;
import com.ecommerce.giftshopbackend.domain.auth.LoginResponseDTO; // Asegúrate de que la ruta sea correcta si moviste los DTOs
import com.ecommerce.giftshopbackend.domain.exception.AuthenticationException; // Necesitaremos esta excepción
import com.ecommerce.giftshopbackend.domain.exception.DataIntegrityException;
import com.ecommerce.giftshopbackend.domain.exception.ResourceNotFoundException; // Y esta también
import com.ecommerce.giftshopbackend.domain.sesion.SesionActivaInsertDTO;
import com.ecommerce.giftshopbackend.domain.sesion.SesionActivaRepository;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioCredencialesDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioDTO;
import com.ecommerce.giftshopbackend.domain.usuario.UsuarioRepository; // Importar la interfaz del repositorio desde el dominio

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
// Necesitaremos dependencias para JWT después, como io.jsonwebtoken.Jwts, etc.

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service // Esto le dice a Spring que es un componente de servicio
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider; // Necesitaremos un proveedor de JWT
    private final SesionActivaRepository sesionActivaRepository; // Necesitaremos un repositorio para Sesiones Activas, probablemente en infrastructure.auth o infrastructure.session

    // Constructor con inyección de dependencias
    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, SesionActivaRepository sesionActivaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.sesionActivaRepository = sesionActivaRepository;
    }

    @Override
    public LoginResponseDTO authenticateUserByPassword(String email, String password, String ip, String userAgent, String origen, boolean esMovil, String plataforma) {
        // 1. Encontrar al usuario por email
        // Usamos Optional.orElseThrow con la excepción personalizada ¡Justo lo que hablábamos!
        UsuarioCredencialesDTO credenciales = usuarioRepository.findCredentialsByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        // 2. Verificar la contraseña
        if (credenciales.getPasswordHash() == null || !passwordEncoder.matches(password, credenciales.getPasswordHash())) {
            throw new AuthenticationException("Credenciales inválidas"); // Necesitas una excepción personalizada para autenticación fallida
        }

        // 3. Generar tokens (Access Token, Refresh Token y sesionId)
        String accessToken = jwtTokenProvider.generateToken(email); // o puedes usar credenciales.getId() si prefieres
        // (Dummy) refresh token — para producción, guarda y maneja en DB
        String refreshToken = "refresh-token-" + UUID.randomUUID();
        String hashedRefreshToken = passwordEncoder.encode(refreshToken);
        UUID idSesionUnica = UUID.randomUUID();

        // 4. Guardar el Refresh Token en la tabla SesionActiva
        SesionActivaInsertDTO nuevaSesion;
        nuevaSesion = new SesionActivaInsertDTO();
        nuevaSesion.setUsuarioId(credenciales.getId()); // Asumiendo relación en Usuario
        nuevaSesion.setRefreshToken(hashedRefreshToken); // guardamos el hash
        nuevaSesion.setIdSesionUnico(idSesionUnica); // Generar un UUID único para la sesión
        nuevaSesion.setIp(ip);
        nuevaSesion.setPlataforma(plataforma);
        nuevaSesion.setEsMovil(esMovil);
        nuevaSesion.setUserAgent(userAgent);
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        nuevaSesion.setFechaCreacion(localDateTime);
        nuevaSesion.setFechaExpiracion(localDateTime.plusDays(30));
        sesionActivaRepository.guardarSesion(nuevaSesion);

        // 5. obtenemos el usuarioDTO
        UsuarioDTO usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new DataIntegrityException(
                        "\"Integridad rota: se encontraron credenciales pero no datos completos para email: \" + email"
                ));

        // 5. Devolver el DTO de respuesta
        return new LoginResponseDTO(accessToken, refreshToken, idSesionUnica, usuario);
    }

    @Override
    public void initiateOtpLogin(String email) throws AuthenticationException {

    }

    @Override
    public LoginResponseDTO verifyOtpAndLogin(String email, String otpCode) throws AuthenticationException {
        return null;
    }

    @Override
    public LoginResponseDTO refreshJwtToken(String refreshToken) throws AuthenticationException {
        return null;
    }

    @Override
    public void logout(String tokenOrUserId) {

    }

    // TODO: Implementar método refreshToken

    // TODO: Implementar método logout

    // TODO: Implementar método para login passwordless (OTP)

    // TODO: Implementar método para login federado (OAuth2)

}