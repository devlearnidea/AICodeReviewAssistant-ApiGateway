package com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception;

public class RetryableResponseException
        extends RuntimeException
{
    private final int status;

    public RetryableResponseException(int status)
    {
        super("Retrying due to status " + status);
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }
}