package com.ecommerce.giftshopbackend.util;

public record RequestMetadata(String ip, String userAgent, String origen, boolean esMovil, String plataforma) {
}
