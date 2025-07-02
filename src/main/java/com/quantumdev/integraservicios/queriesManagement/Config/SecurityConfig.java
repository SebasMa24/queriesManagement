package com.quantumdev.integraservicios.queriesManagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.quantumdev.integraservicios.database.model.ERole;
import com.quantumdev.integraservicios.queriesManagement.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * Security configuration class for the application.
 * Configures security settings, including CORS, CSRF, and request authorization.
 * 
 * @author Nicolás Sabogal
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    /** The JWT authentication filter to be applied to incoming requests */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /** The CORS configuration source to define allowed origins and methods */
    private final CorsConfigurationSource corsConfigurationSource;

    /**
     * Configures the security filter chain for the application.
     * 
     * @param http the HttpSecurity object to configure
     * @return a SecurityFilterChain that defines security settings
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/swagger-ui.html").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/api/domains/**").permitAll()
                    .requestMatchers("/api/operations/hardware/availability").permitAll()
                    .requestMatchers("/api/operations/space/availability").permitAll()
                    .requestMatchers("/api/operations/**").authenticated()
                    .requestMatchers("/api/resources/**").authenticated()
                    .anyRequest().hasAuthority(ERole.ROLE_ADMIN.name())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        }
}
