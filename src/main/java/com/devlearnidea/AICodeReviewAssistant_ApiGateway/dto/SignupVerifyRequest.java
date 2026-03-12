package com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto;

import lombok.Data;

@Data
public class SignupVerifyRequest {

    private String token;
    private String otp;

}