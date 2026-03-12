package com.devlearnidea.AICodeReviewAssistant_ApiGateway.service;

import com.devlearnidea.AICodeReviewAssistant_ApiGateway.config.MicroWebClientService;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.AppUser;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.MfaToken;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.SignupOtpToken;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.TenantContextDTO;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception.CustomException;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils.ConstantProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserClientService {

    private final MicroWebClientService microWebClientService;
    private final ConstantProperties constantProperties;

    private TenantContextDTO tenantContext() {
        return new TenantContextDTO(
                constantProperties.getDefaultTenant(),
                "System"
        );
    }

    // --- USER EXISTS ---
    public Boolean existsByUsername(String username) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/exists/%s";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<Boolean>() {},
                null,
                HttpMethod.GET,
                false,
                tenantContext(),
                url,
                Map.of("username", username)
        );
    }

    // --- SAVE SIGNUP OTP ---
    public SignupOtpToken saveSignupOtp(SignupOtpToken token) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/signup/otp";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<SignupOtpToken>() {},
                token,
                HttpMethod.POST,
                null,
                tenantContext(),
                url,
                new HashMap<>()
        );
    }

    // --- GET SIGNUP OTP ---
    public SignupOtpToken getSignupOtp(String token) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/signup/otp/%s";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<SignupOtpToken>() {},
                null,
                HttpMethod.GET,
                null,
                tenantContext(),
                url,
                Map.of("token", token)
        );
    }

    // --- DELETE SIGNUP OTP ---
    public Boolean deleteSignupOtp(String token) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/signup/otp/%s";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<Boolean>() {},
                null,
                HttpMethod.DELETE,
                false,
                tenantContext(),
                url,
                Map.of("token", token)
        );
    }

    // --- SAVE USER ---
    public AppUser saveUser(AppUser user) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<AppUser>() {},
                user,
                HttpMethod.POST,
                null,
                tenantContext(),
                url,
                new HashMap<>()
        );
    }

    // --- SAVE MFA TOKEN ---
    public MfaToken saveMfaToken(MfaToken token) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/mfa/token";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<MfaToken>() {},
                token,
                HttpMethod.POST,
                null,
                tenantContext(),
                url,
                new HashMap<>()
        );
    }

    // --- GET MFA TOKEN ---
    public MfaToken getMfaToken(String token) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/mfa/token/%s";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<MfaToken>() {},
                null,
                HttpMethod.GET,
                null,
                tenantContext(),
                url,
                Map.of("token", token)
        );
    }

    // --- DELETE MFA TOKEN ---
    public Boolean deleteMfaToken(String token) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/mfa/token/%s";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<Boolean>() {},
                null,
                HttpMethod.DELETE,
                false,
                tenantContext(),
                url,
                Map.of("token", token)
        );
    }

    // --- GET USER BY USERNAME ---
    public AppUser getUserByUsername(String username) throws CustomException {

        String url = constantProperties.getCoreEngineServiceUrl() +
                "user/%s";

        return microWebClientService.apiCallSingleObjectWithSingleReturn(
                new TypeReference<AppUser>() {},
                null,
                HttpMethod.GET,
                null,
                tenantContext(),
                url,
                Map.of("username", username)
        );
    }
}