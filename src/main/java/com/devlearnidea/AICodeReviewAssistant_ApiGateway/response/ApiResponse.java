package com.devlearnidea.AICodeReviewAssistant_ApiGateway.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>
        implements Serializable
{

    private Status status;
    private String message;
    private T response;

    public enum Status
    {
        SUCCESS, FAILED, EXCEPTION, USER_DEFINED_ERROR
    }
}
