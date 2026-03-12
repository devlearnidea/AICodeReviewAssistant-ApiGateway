package com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(AccessDeniedException.class)
    public Mono<Void> handleAccessDeniedException(
            AccessDeniedException ex, ServerWebExchange exchange)
    {

        return Mono.fromRunnable(() ->
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
        // Set appropriate response body or headers if needed

    }
}

