package com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupOtpToken {

    private String id;
    private String token;
    private String username;
    private String password;
    private String email;
    private String otp;
    private LocalDateTime expiry;

}