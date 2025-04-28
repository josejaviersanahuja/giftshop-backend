# File System
```bash
.
├── HELP.md                                      # Archivo de ayuda del proyecto
├── logs                                         # Directorio para los archivos de log
│   ├── giftshop-app.log                         # Log principal de la aplicación
│   └── giftshop-severe.log                      # Log para errores graves
├── mvnw                                         # Wrapper de Maven para Linux/macOS
├── mvnw.cmd                                     # Wrapper de Maven para Windows
├── pom.xml                                      # Archivo de configuración de Maven
├── README.md                                    # Archivo principal de documentación del proyecto
├── set-env-dev.sh                               # Script para configurar variables de entorno de desarrollo
└── src                                          # Directorio principal de código fuente
    ├── main                                     # Código fuente principal de la aplicación
    │   ├── java                                 # Código fuente Java
    │   │   └── com
    │   │       └── ecommerce
    │   │           └── giftshopbackend          # Paquete raíz de la aplicación
    │   │               ├── api                  # Capa de controladores REST
    │   │               │   └── UsuarioController.java # Controlador para endpoints de usuario
    │   │               ├── config               # Clases de configuración de Spring
    │   │               │   ├── GlobalExceptionHandler.java # Manejador global de errores
    │   │               │   ├── RateLimitConfig.java # Configuración de limitación de tasa (Bucket4j)
    │   │               │   ├── SecurityConfig.java # Configuración de Spring Security
    │   │               │   └── WebConfig.java     # Configuración web general (CORS, etc.)
    │   │               ├── domain               # Lógica de negocio (Servicios, Entidades, Interfaces de Repositorio)
    │   │               │   ├── log              # Dominio relacionado con el log de errores
    │   │               │   │   ├── LogError.java      # Entidad/POJO para el log de errores
    │   │               │   │   ├── LogErrorRepository.java # Interfaz del repositorio de log de errores
    │   │               │   │   └── LogErrorService.java   # Servicio de negocio para manejar log de errores
    │   │               │   └── usuario            # Dominio relacionado con usuarios
    │   │               │       ├── UsuarioDTO.java      # DTO para datos de usuario
    │   │               │       ├── Usuario.java         # Entidad/Modelo principal de usuario
    │   │               │       ├── UsuarioPublicDTO.java # DTO público de usuario
    │   │               │       ├── UsuarioRepository.java # Interfaz del repositorio de usuario
    │   │               │       └── UsuarioService.java    # Servicio de negocio para manejar usuarios
    │   │               ├── filter               # Filtros HTTP (ej: Rate Limiting)
    │   │               │   └── RateLimitFilter.java # Filtro para aplicar limitación de tasa
    │   │               ├── GiftshopBackendApplication.java # Clase principal de la aplicación Spring Boot
    │   │               └── infrastructure       # Capa de infraestructura (Implementaciones de Repositorios, DB Access)
    │   │                   ├── log              # Implementación de repositorio de log de errores
    │   │                   │   └── LogErrorRepositoryImpl.java # Implementación jOOQ del repositorio de log de errores
    │   │                   └── usuario            # Implementación de repositorio de usuario
    │   │                       └── UsuarioRepositoryImpl.java # Implementación jOOQ del repositorio de usuario
    │   └── resources                              # Recursos de la aplicación (configuraciones, estáticos, plantillas)
    │       ├── application-dev.properties         # Propiedades de configuración para entorno dev
    │       ├── application-enviroment.properties.example # Ejemplo de propiedades de entorno
    │       ├── application-prod.properties        # Propiedades de configuración para entorno prod
    │       ├── application.properties             # Propiedades de configuración generales
    │       ├── application-test.properties        # Propiedades de configuración para tests
    │       ├── logback-spring.xml                 # Configuración de logging (Logback)
    │       ├── static                             # Archivos estáticos (si los hubiera)
    │       └── templates                          # Plantillas de vistas (si las hubiera)
    └── test                                       # Código fuente para tests
        └── java                                 # Código fuente Java para tests
            └── com
                └── ecommerce
                    └── giftshopbackend          # Paquete raíz de los tests
                        ├── api                  # Tests para la capa de controladores
                        │   └── UsuarioControllerTest.java # Tests unitarios del controlador de usuario (con MockMvc)
                        ├── GiftshopBackendApplicationTests.java # Test de contexto completo de la aplicación Spring Boot
                        └── test                 # Directorio para configuraciones/utilidades de test
                            └── config           # Configuraciones específicas para tests
                                └── TestLoggingConfig.java # Configuración para proveer mocks en tests
```

### Dependencias de Importación (Giftshop Backend) - Actualizado

Aquí se detalla cómo interactúan algunas clases clave (incluyendo las añadidas/modificadas):

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

* **`SRC/config/GlobalExceptionHandler.java`** (Nuevo)
    * Importa desde `SRC/domain/log/`:
        * `LogErrorService.java`
        * `LogError.java`
    * Importa `jakarta.servlet.http.HttpServletRequest` (para Spring Boot 3+).
    * Importa clases de Spring (`@ControllerAdvice`, `@ExceptionHandler`, `WebRequest`, `ServletWebRequest`, `ResponseEntityExceptionHandler`, `HttpStatus`, `ResponseEntity`).
    * Importa clases de logging (`org.slf4j.Logger`, `org.slf4j.LoggerFactory`).
    * Importa clases de Java IO (`java.io.PrintWriter`, `java.io.StringWriter`).
    * Importa clases de Java Time (`java.time.Instant`).
    * Depende (a través de inyección) de `LogErrorService`.

* **`SRC/config/SecurityConfig.java`** (Modificado)
    * Importa clases de Spring Security (`HttpSecurity`, `SecurityFilterChain`, `AbstractHttpConfigurer`, `User`, `UserDetails`, `UserDetailsService`, `PasswordEncoder`, `BCryptPasswordEncoder`, `InMemoryUserDetailsManager`, `AntPathRequestMatcher`).
    * Importa clases de Spring Context (`@Configuration`, `@Bean`).
    * Importa `static org.springframework.security.config.Customizer.withDefaults`.
    * (La mayor parte de sus dependencias son internas a Spring Security o del JDK).

* **`SRC/domain/log/LogError.java`** (Nuevo)
    * Importa clases de Java Time (`java.time.Instant`).
    * (Es un POJO simple, sin dependencias de otras clases de dominio/infraestructura).

* **`SRC/domain/log/LogErrorRepository.java`** (Nuevo)
    * Importa desde `SRC/domain/log/`:
        * `LogError.java`
    * (Es una interfaz, define un contrato para persistir `LogError`).

* **`SRC/domain/log/LogErrorService.java`** (Nuevo, Modificado)
    * Importa desde `SRC/domain/log/`:
        * `LogError.java`
        * `LogErrorRepository.java` (la interfaz)
    * Importa anotaciones de Spring (`@Service`).
    * Depende (a través de inyección) de `LogErrorRepository`.

* **`SRC/domain/usuario/UsuarioService.java`** (Modificado)
    * Importa desde `SRC/infrastructure/usuario/`:
        * `UsuarioRepositoryImpl.java`
    * Importa desde `SRC/domain/usuario/`:
        * `Usuario.java`
        * `UsuarioPublicDTO.java`
        * `UsuarioRepository.java` (la interfaz)
    * Importa anotaciones de Spring (`@Service`, `@Transactional`).
    * Importa utilidades de Java (`java.util.List`, `java.util.Optional`).
    * Depende (a través de inyección) de `UsuarioRepository`.

* **`SRC/infrastructure/log/LogErrorRepositoryImpl.java`** (Nuevo, Modificado)
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

* **`SRC/infrastructure/usuario/UsuarioRepositoryImpl.java`** (Modificado)
    * Importa desde `SRC/domain/usuario/`:
        * `Usuario.java`
        * `UsuarioPublicDTO.java`
        * `UsuarioRepository.java` (la interfaz)
    * Importa clases de jOOQ (`DSLContext`, `DSL`, `field`).
    * Importa las clases de tabla generadas por jOOQ (ej: `static com.ecommerce.giftshopbackend.jooq.tables.UsuarioDetalle.USUARIO_DETALLE`).
    * Importa anotaciones de Spring (`@Repository`).
    * Importa utilidades de Java (`java.util.Optional`, `java.util.List`, `java.util.stream.Collectors`, `java.time.LocalDateTime`).
    * Implementa `UsuarioRepository`.
    * Depende (a través de inyección) de `DSLContext`.

* **`SRC/test/java/com/ecommerce/giftshopbackend/api/UsuarioControllerTest.java`** (Modificado)
    * Importa desde `SRC/domain/log/`:
        * `LogError.java`
        * `LogErrorService.java`
    * Importa desde `SRC/domain/usuario/`:
        * `Usuario.java`
        * `UsuarioPublicDTO.java`
        * `UsuarioService.java`
    * Importa desde `SRC/test/config/`:
        * `TestLoggingConfig.java`
    * Importa clases de testing de Spring Boot (`@WebMvcTest`, `@Autowired`, `@Import`, `MockMvc`).
    * Importa anotaciones y clases de JUnit Jupiter (`@Test`, `@DisplayName`, `@ExtendWith`, `SpringExtension`, `assertEquals`, `assertTrue`, `assertNotNull`).
    * Importa clases y utilidades de Mockito (`Mockito`, `ArgumentCaptor`).
    * Importa utilidades de Jackson (`ObjectMapper`).
    * Importa utilidades de Spring HTTP (`MediaType`).
    * Importa static de MockMvc builders (`get`, `put`, etc.).
    * Importa static de MockMvc result matchers (`status`, etc.).
    * Importa static de Spring Security test utilities (`user`, `with`).
    * Importa utilidades de Java (`java.util.Optional`).
    * Depende (a través de inyección) de `MockMvc`, `ObjectMapper`, `UsuarioService` (mock), y `LogErrorService` (mock).

* **`SRC/test/java/com/ecommerce/giftshopbackend/test/config/TestLoggingConfig.java`** (Nuevo)
    * Importa desde `SRC/domain/log/`:
        * `LogErrorService.java`
    * Importa desde `SRC/domain/usuario/`:
        * `UsuarioService.java`
    * Importa anotaciones de Spring Context (`@Configuration`, `@Bean`).
    * Importa anotaciones de Spring Boot Test (`@TestConfiguration`).
    * Importa `Mockito`.
    * Define `@Bean`s que son mocks de `LogErrorService` y `UsuarioService`, proveyéndolos al contexto de test.