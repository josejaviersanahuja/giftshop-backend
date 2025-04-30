# File System
```bash
.
├── HELP.md # Archivo de ayuda general del proyecto. Útil para novatos... o para mí cuando olvido dónde dejé mis granadas.
├── logs
│   ├── giftshop-app.log # ¡Los logs! El diario de batalla de nuestra app. Crucial para saber qué está pasando (o qué explotó).
│   └── giftshop-severe.log # Logs de errores severos. Aquí es donde buscaremos si las cosas se ponen feas de verdad.
├── mvnw # Script Wrapper de Maven para Linux/macOS. Permite ejecutar Maven sin instalarlo globalmente. ¡Conveniente!
├── mvnw.cmd # Script Wrapper de Maven para Windows. Lo mismo, pero para los que usan... eso.
├── pom.xml # ¡El corazón del proyecto Maven! Define dependencias, plugins y la estructura de construcción. Nuestro mapa del tesoro de librerías.
├── README.md # El cartel de "Bienvenido" del proyecto. Explica qué es, cómo usarlo, etc. ¡Importante para que otros (o tú mismo en el futuro) no se pierdan!
├── set-env-dev.sh # Script para configurar variables de entorno en desarrollo. Probablemente aquí escondes las claves secretas y la configuración de DB local.
└── src
    ├── main
    │   ├── java # Aquí vive el código fuente Java de la aplicación principal. ¡Donde ocurre la magia!
    │   │   └── com
    │   │       └── ecommerce
    │   │           └── giftshopbackend # El paquete base de tu aplicación. ¡Todo nace aquí!
    │   │               ├── api # La capa de API/Presentación. Los Controladores que reciben las peticiones HTTP. ¡La puerta principal!
    │   │               │   ├── AuthController.java # Controlador para autenticación (login, etc.). ¡El portero de la disco!
    │   │               │   └── UsuarioController.java # Controlador para gestionar usuarios. ¡El encargado de la lista VIP!
    │   │               ├── config # Archivos de configuración de Spring y otras cosas. Cómo se comporta nuestra app.
    │   │               │   ├── GlobalExceptionHandler.java # Manejador global de excepciones. Atrapa los errores para que no exploten en la cara del usuario. ¡Nuestro equipo de limpieza!
    │   │               │   ├── RateLimitConfig.java # Configuración para limitar la tasa de peticiones. ¡Para que no nos saturen!
    │   │               │   ├── SecurityConfig.java # Configuración principal de Spring Security. ¡Nuestra fortaleza impenetrable (esperemos)!
    │   │               │   └── WebConfig.java # Configuraciones web adicionales (CORS, etc.). Cómo interactuamos con el mundo exterior.
    │   │               ├── domain # La Capa de Dominio. El corazón de la lógica de negocio. ¡La verdad pura y dura!
    │   │               │   ├── auth # Sub-dominio de autenticación.
    │   │               │   │   ├── AuthService.java # Interfaz del servicio de autenticación (en el dominio, ¡bien!).
    │   │               │   │   ├── LoginRequestDTO.java # DTO para la petición de login. Los datos que llegan.
    │   │               │   │   └── LoginResponseDTO.java # DTO para la respuesta de login. Los datos que enviamos de vuelta.
    │   │               │   ├── exception # Excepciones personalizadas del dominio. Para errores con significado de negocio.
    │   │               │   │   ├── AuthenticationException.java # Error al autenticar. ¡Credenciales incorrectas!
    │   │               │   │   ├── ResourceNotFoundException.java # Error: no encontramos el recurso. ¡No está donde debería estar!
    │   │               │   │   └── UnauthorizedException.java # Error: no tienes permiso. ¡Fuera de aquí!
    │   │               │   ├── log # Sub-dominio de logging de errores (el de negocio).
    │   │               │   │   ├── LogError.java # Entidad/Modelo para el log de errores en DB. ¡El formato del diario!
    │   │               │   │   ├── LogErrorRepository.java # Interfaz del repositorio de logs (en el dominio). ¡Cómo buscamos entradas en el diario!
    │   │               │   │   └── LogErrorService.java # Interfaz del servicio de logs (en el dominio). ¡Cómo interactuamos con el diario!
    │   │               │   └── usuario # Sub-dominio de usuarios.
    │   │               │   │   ├── UsuarioDTO.java # DTO para representar datos de usuario (adaptado para API/frontend). ¡La versión presentable!
    │   │               │   │   ├── Usuario.java # Entidad Usuario. La representación completa en la DB. ¡La verdad desnuda!
    │   │               │   │   ├── UsuarioPublicDTO.java # DTO para la información pública del usuario. ¡La versión para cotillas!
    │   │               │   │   ├── UsuarioRepository.java # Interfaz del repositorio de usuarios (en el dominio). ¡Cómo buscamos usuarios!
    │   │               │   │   └── UsuarioService.java # Interfaz del servicio de usuario (en el dominio). ¡Cómo interactuamos con usuarios a nivel de negocio!
    │   │               ├── filter # Filtros de Servlet/Spring. Interceptan las peticiones antes de que lleguen a los controladores.
    │   │               │   └── RateLimitFilter.java # Filtro para aplicar la limitación de tasa. ¡El tipo de seguridad en la entrada!
    │   │               ├── GiftshopBackendApplication.java # La clase principal de la aplicación Spring Boot. ¡Donde todo arranca!
    │   │               └── infrastructure # La Capa de Infraestructura. Implementaciones que interactúan con el mundo exterior (DB, APIs externas). ¡Los trabajadores duros!
    │   │                   ├── auth # Implementaciones de infraestructura para autenticación.
    │   │                   │   └── AuthServiceImpl.java # Implementación del servicio de autenticación. ¡Cómo autenticamos usando DB, PasswordEncoder, etc.! (Aquí lo pusimos, ¡coherente con tu patrón!)
    │   │                   ├── log # Implementaciones de infraestructura para logging de errores.
    │   │                   │   └── LogErrorRepositoryImpl.java # Implementación del repositorio de logs (interactúa con la DB). ¡El que escribe en el diario de la DB!
    │   │                   └── usuario # Implementaciones de infraestructura para usuarios.
    │   │                       └── UsuarioRepositoryImpl.java # Implementación del repositorio de usuarios (interactúa con la DB usando jOOQ). ¡El que habla con la tabla de usuarios!
    │   └── resources # Recursos no Java (archivos de configuración, plantillas, estáticos).
    │       ├── application-dev.properties # Configuración específica para el entorno de desarrollo.
    │       ├── application-enviroment.properties.example # Ejemplo de configuración para entornos.
    │       ├── application-prod.properties # Configuración específica para producción.
    │       ├── application.properties # Configuración por defecto.
    │       ├── application-test.properties # Configuración específica para tests.
    │       ├── logback-spring.xml # Configuración de logging. Cómo se ven (o no) nuestros mensajes en los logs.
    │       ├── static # Archivos estáticos que podrían servirse directamente.
    │       └── templates # Plantillas para vistas (si usas alguna).
    └── test # Código fuente para los tests. ¡Donde probamos que nuestra magia funciona y no explota!
        └── java
            └── com
                └── ecommerce
                    └── giftshopbackend # Paquete base de tests.
                        ├── api # Tests para los controladores.
                        │   └── UsuarioControllerTest.java # Tests para el controlador de usuarios. ¡Probando al portero!
                        ├── GiftshopBackendApplicationTests.java # Tests de contexto de Spring Boot. ¡Probando que la app arranca!
                        └── test # Un paquete 'test' dentro de 'test'? Okay, es un poco redundante, pero si te gusta...
                            └── config # Configuraciones específicas para tests.
                                └── TestLoggingConfig.java # Configuración de logging para tests.

31 directories, 42 files
```

### Dependencias de Importación (Giftshop Backend) - Actualizado

Aquí se detalla cómo interactúan las clases clave, incluyendo todas las dependencias de tu reporte original más las añadidas/modificadas en esta sesión.

* **`SRC/GiftshopBackendApplication.java`**
    * Importa implícitamente todos los controladores anotados con `@RestController` dentro del paquete `SRC/api/`.
    * (Probablemente importa clases de Spring Boot main como `SpringApplication`).

* **`SRC/api/UsuarioController.java`**
    * Importa desde `SRC/domain/usuario/`:
        * `Usuario.java`
        * `UsuarioPublicDTO.java`
        * `UsuarioService.java`
    * (Probablemente importa anotaciones de Spring Web como `@RestController`, `@RequestMapping`, `@PathVariable`, `@RequestBody`, y clases como `ResponseEntity`, `HttpStatus`).
    * (TEMPORALMENTE: Importa `ResponseEntity`, `HttpStatus` para el endpoint de test `causeErrorEndpoint`).

* **`SRC/config/GlobalExceptionHandler.java`** (Modificado en la Sesión)
    * Importa desde `SRC/domain/log/`:
        * `LogErrorService.java`
        * `LogError.java`
    * Importa `jakarta.servlet.http.HttpServletRequest` (para Spring Boot 3+).
    * Importa clases de Spring (`@ControllerAdvice`, `@ExceptionHandler`, `ResponseEntityExceptionHandler`, `HttpStatus`, `ResponseEntity`).
    * Importa clases de logging (`org.slf4j.Logger`, `org.slf4j.LoggerFactory`).
    * Importa clases de Java IO (`java.io.PrintWriter`, `java.io.StringWriter`).
    * Importa clases de Java Time (`java.time.Instant`).
    * Depende (a través de inyección) de `LogErrorService`.
    * **Dependencias Añadidas en Sesión:**
        * Importa desde `SRC/domain/exception/`: `AuthenticationException`, `ResourceNotFoundException`, `UnauthorizedException`.
        * Importa de Java Util: `java.util.NoSuchElementException`.
        * Importa de Spring Security: `org.springframework.security.access.AccessDeniedException`.

* **`SRC/config/SecurityConfig.java`** (Modificado en la Sesión)
    * Importa clases de Spring Security (`HttpSecurity`, `SecurityFilterChain`, `AbstractHttpConfigurer`, `User`, `UserDetails`, `UserDetailsService`, `PasswordEncoder`, `BCryptPasswordEncoder`, `InMemoryUserDetailsManager`, `AntPathRequestMatcher`).
    * Importa clases de Spring Context (`@Configuration`, `@Bean`).
    * Importa `static org.springframework.security.config.Customizer.withDefaults`.
    * (La mayor parte de sus dependencias son internas a Spring Security o del JDK).
    * **Dependencias Añadidas en Sesión:** (Basado en modificaciones típicas, no específicas de nuestro código exacto si no lo tocamos mucho). Puede incluir dependencias para JWT si se empezó a integrar aquí.

* **`SRC/domain/log/LogError.java`** (Nuevo en tu lista base, no modificado en sesión)
    * Importa clases de Java Time (`java.time.Instant`).
    * (Es un POJO simple, sin dependencias de otras clases de dominio/infraestructura).

* **`SRC/domain/log/LogErrorRepository.java`** (Nuevo en tu lista base, no modificado en sesión)
    * Importa desde `SRC/domain/log/`:
        * `LogError.java`
    * (Es una interfaz, define un contrato para persistir `LogError`).

* **`SRC/domain/log/LogErrorService.java`** (Nuevo en tu lista base, Modificado)
    * Importa desde `SRC/domain/log/`:
        * `LogError.java`
        * `LogErrorRepository.java` (la interfaz)
    * Importa anotaciones de Spring (`@Service`).
    * Depende (a través de inyección) de `LogErrorRepository`.
    * **Dependencias Añadidas en Sesión:** (No añadimos dependencias significativas aquí, más allá de refactorizar si se hizo).

* **`SRC/domain/usuario/UsuarioService.java`** (Modificado en la Sesión)
    * Importa desde `SRC/infrastructure/usuario/`:
        * `UsuarioRepositoryImpl.java` (Nota: Idealmente, un servicio de dominio solo importaría la *interfaz* `UsuarioRepository` de `domain.usuario`, no la implementación de `infrastructure`. Revisa si este import es correcto en tu código).
    * Importa desde `SRC/domain/usuario/`:
        * `Usuario.java`
        * `UsuarioPublicDTO.java`
        * `UsuarioRepository.java` (la interfaz)
    * Importa anotaciones de Spring (`@Service`, `@Transactional`).
    * Importa utilidades de Java (`java.util.List`, `java.util.Optional`).
    * Depende (a través de inyección) de `UsuarioRepository`.
    * **Dependencias Añadidas en Sesión:** (No modificamos significativamente este archivo en nuestras últimas interacciones directas sobre Auth, pero si tú lo modificaste, aquí irían sus nuevas dependencias).

* **`SRC/infrastructure/log/LogErrorRepositoryImpl.java`** (Nuevo en tu lista base, Modificado)
    * Importa desde `SRC/domain/log/`:
        * `LogError.java`
        * `LogErrorRepository.java` (la interfaz)
    * Importa clases de jOOQ (`DSLContext`, `DSL`).
    * Importa las clases de tabla generadas por jOOQ (ej: `static com.ecommerce.giftshopbackend.jooq.tables.LogError.LOG_ERROR`).
    * Importa clases de Java Time (`java.time.LocalDateTime`, `java.time.ZoneOffset`, `java.time.LocalDate`).
    * Importa clases de Java SQL (`java.sql.Timestamp`, `java.sql.Date`).
    * Importa anotaciones de Spring (`@Repository`).
    * Implementa `LogErrorRepository`.
    * Depende (a través de inyección) de `DSLContext`.
    * **Dependencias Añadidas en Sesión:** (No modificamos significativamente este archivo en nuestras últimas interacciones directas).

* **`SRC/infrastructure/usuario/UsuarioRepositoryImpl.java`** (Modificado en la Sesión)
    * Implementa `UsuarioRepository` (de `domain.usuario`).
    * Importa desde `SRC/domain/usuario/`:
        * `Usuario.java`
        * `UsuarioPublicDTO.java`
        * `UsuarioRepository.java` (la interfaz).
        * `UsuarioDTO`.
    * Importa de Spring Context: `@Repository`.
    * Importa de jOOQ: `org.jooq.DSLContext`, `org.jooq.impl.DSL` (`field`, `select`, `using`, etc. - estático).
    * Implementa `UsuarioRepository`.
    * Depende (a través de inyección) de: `DSLContext`.
    * **Dependencias Añadidas en Sesión:**
        * Importa de Java Util: `java.util.Optional`, `java.util.List`.
        * Importa de Java Time: `java.time.LocalDateTime`, `java.time.Instant`, `java.time.LocalDate`.
        * Importa de Java Math: `java.math.BigDecimal`.
        * Importa de Java SQL: `java.sql.Date` (posiblemente para mapeo).
        * Importa las clases de tabla generadas por jOOQ (estático `com.ecommerce.giftshopbackend.jooq.tables.*` para `USUARIO`, `USUARIO_DETALLE`, `GENERO`).
        * Importa utilidades de Java: `java.util.stream.Collectors` (si lo usaste en algún método).

---

#### **Dependencias completamente Nuevas (Archivos Nuevos en Sesión)**

* **`src/main/java/com/ecommerce/giftshopbackend/api/AuthController.java`** (Nuevo Archivo)
    * Importa desde `domain.auth`: `AuthService`, `LoginRequestDTO`, `LoginResponseDTO`.
    * Importa de Spring Web: `@RestController`, `@RequestMapping`, `@PostMapping`, `@RequestBody`, `ResponseEntity`.
    * Importa de Java Validation: `javax.validation.Valid`.
    * Depende (por inyección) de: `AuthService`.

* **`src/main/java/com/ecommerce/giftshopbackend/domain/auth/AuthService.java`** (Nuevo Archivo)
    * Importa desde `domain.dto.auth`: `LoginResponseDTO`.
    * Importa desde `domain.exception`: `AuthenticationException`.
    * (Es una interfaz).

* **`src/main/java/com/ecommerce/giftshopbackend/domain/auth/LoginRequestDTO.java`** (Nuevo Archivo)
    * Importa de `jakarta.validation.constraints`: `Email`, `NotBlank`, `Size`.
    * (Es un POJO).

* **`src/main/java/com/ecommerce/giftshopbackend/domain/auth/LoginResponseDTO.java`** (Nuevo Archivo)
    * (Es un POJO).

* **`src/main/java/com/ecommerce/giftshopbackend/domain/exception/AuthenticationException.java`** (Nuevo Archivo)
    * Extiende `RuntimeException`.

* **`src/main/java/com/ecommerce/giftshopbackend/domain/exception/ResourceNotFoundException.java`** (Nuevo Archivo)
    * Extiende `RuntimeException`.
    * (Potencialmente importa `@ResponseStatus`).

* **`src/main/java/com/ecommerce/giftshopbackend/domain/exception/UnauthorizedException.java`** (Nuevo Archivo)
    * Extiende `RuntimeException`.

* **`src/main/java/com/ecommerce/giftshopbackend/infrastructure/auth/AuthServiceImpl.java`** (Nuevo Archivo)
    * Implementa `AuthService` (de `domain.auth`).
    * Importa desde `domain.auth`: `AuthService`, `LoginResponseDTO`.
    * Importa desde `domain.exception`: `AuthenticationException`, `ResourceNotFoundException`.
    * Importa desde `domain.usuario`: `UsuarioRepository` (la interfaz).
    * Importa de Spring Security: `org.springframework.security.crypto.password.PasswordEncoder`.
    * Importa de Spring Context: `@Service`.
    * Importa de Java Util: `java.util.Optional`, `java.util.UUID`.
    * Importa de Java Time: `java.time.Instant`, `java.time.LocalDate`, `java.time.LocalDateTime`.
    * Importa de Java Math: `java.math.BigDecimal`.
    * Importa las clases de tabla generadas por jOOQ (para el método `findByEmail` que usa el repo).
    * Importa de jOOQ DSL (estáticos).
    * Depende (por inyección) de: `UsuarioRepository`, `PasswordEncoder` (y futuros `JwtTokenProvider`, `SesionActivaRepository`, `DSLContext`).
