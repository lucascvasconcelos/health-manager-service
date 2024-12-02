package com.healthmanagerservice.healthmanagerservice.presentation.controllers;

import com.healthmanagerservice.healthmanagerservice.application.service.UserService;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.request.UserRequestDTO;
import com.healthmanagerservice.healthmanagerservice.presentation.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.createUser(request);
        return ResponseEntity.status(201).body(response);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
