package com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils;

import com.devlearnidea.AICodeReviewAssistant_ApiGateway.response.ApiResponse;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@Data
public class HttpUtilDTO
{
    HttpHeaders headers;
    HttpEntity<String> requestEntity;
    ParameterizedTypeReference<ApiResponse<?>> responseType;
}
