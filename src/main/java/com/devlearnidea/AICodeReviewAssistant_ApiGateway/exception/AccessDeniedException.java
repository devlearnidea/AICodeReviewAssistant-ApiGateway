package com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception;

public class AccessDeniedException
        extends RuntimeException
{
    public AccessDeniedException(String message)
    {
        super(message);
    }
}
