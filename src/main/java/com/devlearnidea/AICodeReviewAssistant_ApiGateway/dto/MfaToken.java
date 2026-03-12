package com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MfaToken {

    private String id;

    private String token;

    private String userId;

    private String otp;

    private LocalDateTime expiry;

}