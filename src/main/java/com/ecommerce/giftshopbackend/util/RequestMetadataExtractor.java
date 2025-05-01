package com.ecommerce.giftshopbackend.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestMetadataExtractor {

    public static RequestMetadata extract(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        String userAgent = request.getHeader("User-Agent");
        String origen = request.getHeader("X-App-Origin");
        boolean esMovil = UserAgentUtils.esMovil(userAgent);
        String plataforma = UserAgentUtils.detectarPlataforma(userAgent);

        return new RequestMetadata(ip, userAgent, origen, esMovil, plataforma);
    }
}
