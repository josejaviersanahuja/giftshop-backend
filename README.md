# File System
```bash
.
├── HELP.md  # Archivo de ayuda/documentación
├── mvnw  # Script ejecutable de Maven para Linux/Mac (wrapper)
├── mvnw.cmd  # Script ejecutable de Maven para Windows (wrapper)
├── pom.xml  # Archivo de configuración de Maven (Project Object Model)
└── src  # Carpeta que contiene el código fuente del proyecto
    ├── main  # Directorio principal del código fuente (producción)
    │   ├── java  # Código fuente Java
    │   │   └── com  # Paquete base de la aplicación
    │   │       └── ecommerce  # Subpaquete para la aplicación ecommerce
    │   │           └── giftshopbackend  # Subpaquete específico para gift-shop-backend
    │   │               └── GiftshopBackendApplication.java  # Clase principal de la aplicación Java
    │   └── resources  # Archivos de configuración y recursos
    │       ├── application.properties  # Archivo de configuración de la aplicación (por ejemplo, base de datos, etc.)
    │       ├── static  # Archivos estáticos (CSS, JS, imágenes, etc.)
    │       └── templates  # Plantillas (si se usan en el proyecto, por ejemplo, Thymeleaf)
    └── test  # Directorio para el código de pruebas
        └── java  # Código fuente de las pruebas en Java
            └── com  # Paquete base para las pruebas
                └── ecommerce  # Subpaquete para las pruebas de ecommerce
                    └── giftshopbackend  # Subpaquete para las pruebas específicas de gift-shop-backend
                        └── GiftshopBackendApplicationTests.java  # Clase de pruebas para la aplicación

```