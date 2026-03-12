package com.devlearnidea.AICodeReviewAssistant_ApiGateway.service;

import lombok.AllArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendOtpMail(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Signup OTP Verification");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }

}