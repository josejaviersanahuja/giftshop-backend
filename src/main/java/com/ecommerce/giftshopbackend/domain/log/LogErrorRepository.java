// Archivo: src/main/java/com/ecommerce/giftshopbackend/domain/log/LogErrorRepository.java

package com.ecommerce.giftshopbackend.domain.log;

// Esta es solo la interfaz, la implementación está en infrastructure

public interface LogErrorRepository {

    void save(LogError errorLog);

}