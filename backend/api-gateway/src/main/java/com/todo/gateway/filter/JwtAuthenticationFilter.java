package com.todo.gateway.filter;

import com.todo.shared.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (isPublicEndpoint(request.getPath().toString())) {
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header format");
            }

            String token = authHeader.substring(7);

            try {
                if (!jwtUtil.validateToken(token)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
                }

                String username = jwtUtil.extractUsername(token);
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", username)
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                log.error("JWT validation error: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token validation failed");
            }
        };
    }

    private boolean isPublicEndpoint(String path) {
        return path.contains("/auth/login") ||
                path.contains("/auth/register") ||
                path.contains("/actuator") ||
                path.contains("/swagger") ||
                path.contains("/api-docs");
    }

    public static class Config {
    }
}