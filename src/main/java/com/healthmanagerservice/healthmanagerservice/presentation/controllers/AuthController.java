package com.healthmanagerservice.healthmanagerservice.presentation.controllers;

import com.healthmanagerservice.healthmanagerservice.application.service.impl.UserDetailsService;
import com.healthmanagerservice.healthmanagerservice.infrastructure.security.JwtTokenManager;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.AuthRequest;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.error.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authentication = userDetailsService.authenticate(authRequest.username(), authRequest.password());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JwtTokenManager.getInstance().generateToken(authRequest.username(),
                    authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
