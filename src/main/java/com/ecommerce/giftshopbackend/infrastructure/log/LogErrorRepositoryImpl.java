// Archivo: src/main/java/com/ecommerce/giftshopbackend/infrastructure/log/LogErrorRepositoryImpl.java
// ¡Versión 2.0 - Ahora con la importación correcta y manejo de fechas mejorado!
package com.ecommerce.giftshopbackend.infrastructure.log;

import com.ecommerce.giftshopbackend.domain.log.LogError;
import com.ecommerce.giftshopbackend.domain.log.LogErrorRepository;
import org.jooq.DSLContext;
// ¡Corregimos la importación! Usamos tu paquete .jooq.tables
import static com.ecommerce.giftshopbackend.jooq.tables.LogError.LOG_ERROR;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime; // Usamos LocalDateTime como en tu ejemplo funcional
import java.time.ZoneOffset; // Para el cálculo de mes_particion basado en Instant (si mantienes Instant en LogError)
import java.time.LocalDate;

@Repository
public class LogErrorRepositoryImpl implements LogErrorRepository {

    private final DSLContext dslContext;

    public LogErrorRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void save(LogError errorLog) {
        // Aunque LogError usa Instant, vamos a mapear a tipos que jOOQ/Postgres esperan
        // Tu tabla Log_Error tiene TIMESTAMP (WITHOUT TIME ZONE) para 'fecha'
        // y DATE para 'mes_particion'.

        // Mapeamos Instant a LocalDateTime para la columna TIMESTAMP WITHOUT TIME ZONE
        LocalDateTime fechaLocalDateTime = LocalDateTime.ofInstant(errorLog.getFecha(), ZoneOffset.UTC);
        // Si decidiste cambiar LogError para usar LocalDateTime directamente, esto sería más simple

        // Calculamos mes_particion: el primer día del mes del log entry.
        // Aseguramos que sea el primer día y lo convertimos a java.sql.Date para la columna DATE
        LocalDate mesParticionLocalDate = errorLog.getFecha() // Usamos el Instant original
                .atZone(ZoneOffset.UTC) // En la zona UTC
                .toLocalDate()         // Obtener la fecha (sin hora)
                .withDayOfMonth(1);      // Establecer el día al 1ro


        dslContext.insertInto(LOG_ERROR)
                .columns(
                        LOG_ERROR.TIPO,
                        LOG_ERROR.SEVERIDAD,
                        LOG_ERROR.MENSAJE,
                        LOG_ERROR.STACK_TRACE,
                        LOG_ERROR.ENDPOINT,
                        LOG_ERROR.METODO_HTTP,
                        LOG_ERROR.USUARIO_ID,
                        LOG_ERROR.FECHA, // Columna TIMESTAMP
                        LOG_ERROR.MES_PARTICION // Columna DATE
                )
                .values(
                        errorLog.getTipo(),
                        errorLog.getSeveridad(),
                        errorLog.getMensaje(),
                        errorLog.getStackTrace(),
                        errorLog.getEndpoint(),
                        errorLog.getMetodoHttp(),
                        errorLog.getUsuarioId(),
                        fechaLocalDateTime, // Pasamos LocalDateTime para la columna TIMESTAMP
                        mesParticionLocalDate // Pasamos java.sql.Date para la columna DATE
                )
                .execute();
    }
}