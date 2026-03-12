package com.devlearnidea.AICodeReviewAssistant_ApiGateway.service;

import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.AppUser;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.LoginRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.MfaToken;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.MfaVerifyRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.SignupOtpToken;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.SignupRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.SignupVerifyRequest;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception.CustomException;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils.JwtUtil;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClientService userClientService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtUtil jwtUtil;

    public Map<String, String> signup(SignupRequest request)
            throws CustomException
    {
        if (userClientService.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String otp = OtpUtil.generateOtp();
        String token = UUID.randomUUID().toString();

        SignupOtpToken signupOtpToken = new SignupOtpToken(
                null, token, request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                passwordEncoder.encode(otp),
                LocalDateTime.now().plusMinutes(60)
        );

        userClientService.saveSignupOtp(signupOtpToken);
        mailService.sendOtpMail(request.getEmail(), otp);

        return Map.of("signupToken", token);
    }

    public void verifySignup(SignupVerifyRequest request)
            throws CustomException
    {
        SignupOtpToken token = userClientService.getSignupOtp(request.getToken());

        if (token.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!passwordEncoder.matches(request.getOtp(), token.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        AppUser user = new AppUser();
        user.setUsername(token.getUsername());
        user.setPassword(token.getPassword());
        user.setEmail(token.getEmail());

        userClientService.saveUser(user);
        userClientService.deleteSignupOtp(request.getToken());
    }

    public Map<String, String> login(LoginRequest request)
            throws CustomException
    {
        AppUser user = userClientService.getUserByUsername(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (user.isMfaEnabled()) {
            String otp = OtpUtil.generateOtp();
            String mfaToken = UUID.randomUUID().toString();

            MfaToken token = new MfaToken(
                    null, mfaToken, user.getId(),
                    passwordEncoder.encode(otp),
                    LocalDateTime.now().plusMinutes(5)
            );

            userClientService.saveMfaToken(token);
            mailService.sendOtpMail(user.getEmail(), otp);

            return Map.of("mfaToken", mfaToken);
        }

        String jwt = jwtUtil.generateToken(user.getUsername());
        return Map.of("accessToken", jwt);
    }

    public String verifyMfa(MfaVerifyRequest request)
            throws CustomException
    {
        MfaToken token = userClientService.getMfaToken(request.getToken());

        if (token.getExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!passwordEncoder.matches(request.getOtp(), token.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        AppUser user = userClientService.getUserByUsername(token.getUserId());

        String jwt = jwtUtil.generateToken(user.getUsername());
        userClientService.deleteMfaToken(request.getToken());

        return jwt;
    }
}