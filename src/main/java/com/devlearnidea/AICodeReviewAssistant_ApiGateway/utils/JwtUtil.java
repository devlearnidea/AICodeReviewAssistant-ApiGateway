package com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret;
    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secret)
    {
        this.secret = secret;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)               // store username in token
                .setIssuedAt(new Date())            // token creation time
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour validity
                .signWith(key)                      // sign token with key
                .compact();                         // build the token
    }

}