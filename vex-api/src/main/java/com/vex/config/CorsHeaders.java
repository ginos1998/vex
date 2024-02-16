package com.vex.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsHeaders implements WebFilter {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!isValidHeaders(exchange.getRequest())) {
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isValidHeaders(org.springframework.http.server.reactive.ServerHttpRequest request) {
        return request.getHeaders().containsKey("Vex-Application");
    }
}
