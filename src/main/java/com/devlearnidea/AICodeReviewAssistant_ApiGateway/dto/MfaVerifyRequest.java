package com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto;

import lombok.Data;

@Data
public class MfaVerifyRequest {

    private String token;
    private String otp;

}