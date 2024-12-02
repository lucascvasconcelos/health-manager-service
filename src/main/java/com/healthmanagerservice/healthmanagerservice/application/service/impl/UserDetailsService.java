package com.healthmanagerservice.healthmanagerservice.application.service.impl;

import com.healthmanagerservice.healthmanagerservice.domain.entities.User;
import com.healthmanagerservice.healthmanagerservice.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);


        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        logger.info("User loaded: {}, Roles: {}", user.getName(), user.getRole());

        return org.springframework.security.core.userdetails.User.withUsername(user.getName())
                .password(user.getPassword())
                .authorities(String.valueOf(user.getRole()))
                .build();
    }

    public Authentication authenticate(String username, String password) {
        logger.info("Attempting authentication for username: {}", username);

        UserDetails userDetails = loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.info("Authentication successful for username: {}, Roles: {}", username, userDetails.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            return authenticationToken;
        }

        logger.warn("Invalid credentials for username: {}, Roles: {}", username,  userDetails.getAuthorities());
        throw new RuntimeException("Invalid credentials");
    }
}
