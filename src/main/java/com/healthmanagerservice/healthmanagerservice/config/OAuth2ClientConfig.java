package com.healthmanagerservice.healthmanagerservice.config;

import com.healthmanagerservice.healthmanagerservice.application.service.impl.UserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
public class OAuth2ClientConfig {
    private static final String[] PERMIT_ALL_URLS =
            {"/h2-console/**", "/favicon**", "/auth/**", "/login/**"};
    private static final String[] IGNORE_CSRF_URLS =
            {"/h2-console/**", "/auth/**", "/patients/**", "/favicon**", "/http://localhost:8080/"};

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PERMIT_ALL_URLS).permitAll()

                        .requestMatchers("/patients/**").hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN", "ROLE_NURSE")
                        .requestMatchers("/users/**").hasAnyAuthority("ROLE_DOCTOR", "ROLE_ADMIN")
                        .requestMatchers("/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest()
                        .authenticated()
                )

                .csrf(csrf -> csrf.ignoringRequestMatchers(IGNORE_CSRF_URLS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())

                        ).accessDeniedHandler(accessDeniedHandler))
                .requestCache(requestCache -> requestCache.requestCache(new NullRequestCache()))
                .formLogin(AbstractHttpConfigurer::disable)
                .oauth2Login(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
                // todo desativaçao do oauth2 devido a não implementação do frontend
//                .oauth2Login(oauth2 -> oauth2
//                        .clientRegistrationRepository(clientRegistrationRepository())
//                )
//                .logout(logout -> logout
//                        .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository()))
//                );

        return http.build();
    }

    // todo desativaçao do oauth2 devido a não implementação do frontend
//
//    @Bean
//    public OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
//        return new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//    }

//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration clientRegistration = ClientRegistration
//                .withRegistrationId("client")
//                .clientId("client-id")
//                .clientSecret("secret")
//                .scope("read", "write")
//                .authorizationUri("http://localhost:8080/oauth2/authorize")
//                .tokenUri("http://localhost:8080/oauth2/token")
//                .redirectUri("http://localhost:8080/login/oauth2/code/client")
//                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
//                .build();
//
//        return new InMemoryClientRegistrationRepository(clientRegistration);
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        jwtConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtConverter;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }
}
