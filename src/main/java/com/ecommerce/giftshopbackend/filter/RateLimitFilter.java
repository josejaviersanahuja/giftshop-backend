package com.ecommerce.giftshopbackend.filter;

import io.github.bucket4j.Bucket;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

public class RateLimitFilter implements Filter {

    private final Bucket bucket;

    public RateLimitFilter() {
        // Usando la API moderna de la documentación para construir el Bucket y el límite
        this.bucket = Bucket.builder() // Usamos directamente Bucket.builder()
                .addLimit(limit -> limit.capacity(100) // Definimos la capacidad
                        .refillGreedy(100, Duration.ofMinutes(1))) // Definimos el refil
                .build(); // Construimos el Bucket
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Too many requests - Rate limit exceeded");
        }
    }
}
