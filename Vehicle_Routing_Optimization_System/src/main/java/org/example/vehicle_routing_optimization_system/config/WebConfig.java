package org.example.vehicle_routing_optimization_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.security.MessageDigest;

@Configuration
public class WebConfig {
    @Bean
    public OncePerRequestFilter conditionalEtagFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain) throws ServletException, IOException {
                if (!"GET".equalsIgnoreCase(request.getMethod()) && !"HEAD".equalsIgnoreCase(request.getMethod())) {
                    chain.doFilter(request, response);
                    return;
                }
                ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
                chain.doFilter(request, wrapper);
                byte[] body = wrapper.getContentAsByteArray();
                String etag = "\"" + digest(body) + "\"";
                wrapper.setHeader("ETag", etag);
                if (matches(etag, request.getHeader("If-None-Match"))
                        && wrapper.getStatus() >= 200 && wrapper.getStatus() < 300) {
                    wrapper.resetBuffer();
                    wrapper.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
                wrapper.copyBodyToResponse();
            }

            private String digest(byte[] body) {
                try {
                    byte[] hash = MessageDigest.getInstance("SHA-256").digest(body);
                    StringBuilder result = new StringBuilder();
                    for (byte value : hash) result.append(String.format("%02x", value));
                    return result.toString();
                } catch (java.security.NoSuchAlgorithmException exception) {
                    throw new IllegalStateException("SHA-256 is unavailable", exception);
                }
            }

            private boolean matches(String etag, String header) {
                if (header == null) return false;
                for (String candidate : header.split(",")) {
                    String normalized = candidate.trim();
                    String expected = etag.replace("\"", "");
                    String actual = normalized.replace("W/", "").replace("\"", "");
                    if ("*".equals(normalized) || expected.equals(actual)) return true;
                }
                return false;
            }
        };
    }
}