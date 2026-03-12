package com.devlearnidea.AICodeReviewAssistant_ApiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CustomWebFilter
        implements WebFilter
{

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getQueryParams().getFirst("token");
        if (token != null && !token.isEmpty()) {
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("Authorization", "Bearer " + token)
                    .build();
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            return chain.filter(mutatedExchange);
        }

        return chain.filter(exchange);
    }
}
