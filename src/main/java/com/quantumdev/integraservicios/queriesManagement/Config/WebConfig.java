package com.quantumdev.integraservicios.queriesManagement.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for CORS settings.
 * Allows requests from the frontend URL specified in the environment properties.
 * 
 * @author Nicolás Sabogal
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig {

    /** The environment to access properties */
    private final Environment environment;

    /**
     * Configures CORS settings for the application.
     * 
     * @return a CorsConfigurationSource that allows requests from the frontend URL
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of(environment.getProperty("frontend.url")));
            configuration.setAllowedMethods(List.of("GET", "OPTIONS"));
            configuration.setAllowedHeaders(List.of("Authorization"));
            configuration.setAllowCredentials(true);

            return configuration;
        };
    }

}
