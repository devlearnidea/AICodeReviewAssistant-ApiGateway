package com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils;

import java.security.SecureRandom;

public class OtpUtil {

    public static String generateOtp() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }

}