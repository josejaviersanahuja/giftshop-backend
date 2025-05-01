package com.ecommerce.giftshopbackend.util;

public class UserAgentUtils {

    public static boolean esMovil(String userAgent) {
        if (userAgent == null) return false;
        String ua = userAgent.toLowerCase();
        return ua.contains("mobile") || ua.contains("android") || ua.contains("iphone") || ua.contains("ipad");
    }

    public static String detectarPlataforma(String userAgent) {
        if (userAgent == null) return "DESCONOCIDO";

        String ua = userAgent.toLowerCase();

        if (ua.contains("chrome")) return "Chrome";
        if (ua.contains("firefox")) return "Firefox";
        if (ua.contains("safari") && !ua.contains("chrome")) return "Safari";
        if (ua.contains("edge")) return "Edge";
        if (ua.contains("opera") || ua.contains("opr")) return "Opera";
        if (ua.contains("trident") || ua.contains("msie")) return "Internet Explorer";

        return "DESCONOCIDO";
    }
}
