package com.healthmanagerservice.healthmanagerservice.config;

import com.healthmanagerservice.healthmanagerservice.infrastructure.security.JwtTokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;

@Configuration
public class JwtDecoderConfiguration {
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey secretKeySpec = JwtTokenManager.getInstance().getSecretKey();
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }
}
