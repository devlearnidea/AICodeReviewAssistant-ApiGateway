package com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils;


import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.TenantContextDTO;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.response.ApiResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.ParameterizedTypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class HttpUtils
{

    public static HttpUtilDTO createHeaders(TenantContextDTO tenantContextDTO)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Tenant", tenantContextDTO.getTenantId());
        headers.set("X-User", tenantContextDTO.getUserName());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<ApiResponse<?>> responseType = new ParameterizedTypeReference<ApiResponse<?>>()
        {
        };

        HttpUtilDTO httpUtilDTO = new HttpUtilDTO();
        httpUtilDTO.setHeaders(headers);
        httpUtilDTO.setRequestEntity(requestEntity);
        httpUtilDTO.setResponseType(responseType);

        return httpUtilDTO;
        // return headers;
    }

    public static ObjectMapper mapper()
    {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
