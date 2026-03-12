package com.devlearnidea.AICodeReviewAssistant_ApiGateway.controller;

import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.LoginRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.MfaVerifyRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.SignupRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.SignupVerifyRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception.CustomException;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.response.ApiResponse;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{

    private final AuthService authService;

    /**
     * Signup API
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Map<String, String>>> signup(
            @RequestBody SignupRequest request)
            throws CustomException
    {

        Map<String, String> response = authService.signup(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        ApiResponse.Status.SUCCESS,
                        "OTP sent successfully",
                        response
                )
        );
    }

    /**
     * Signup OTP Verification
     */
    @PostMapping("/signup/verify")
    public ResponseEntity<ApiResponse<Void>> verifySignup(
            @RequestBody SignupVerifyRequest request)
            throws CustomException
    {

        authService.verifySignup(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        ApiResponse.Status.SUCCESS,
                        "User created successfully",
                        null
                )
        );
    }

    /**
     * Login API
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(
            @RequestBody LoginRequest request)
            throws CustomException
    {

        Map<String, String> response = authService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        ApiResponse.Status.SUCCESS,
                        "Login successful",
                        response
                )
        );
    }

    /**
     * MFA Verification
     */
    @PostMapping("/mfa/verify")
    public ResponseEntity<ApiResponse<String>> verifyMfa(
            @RequestBody MfaVerifyRequest request)
            throws CustomException
    {

        String jwtToken = authService.verifyMfa(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        ApiResponse.Status.SUCCESS,
                        "MFA verified successfully",
                        jwtToken
                )
        );
    }
}