package com.quantumdev.integraservicios.queriesManagement.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final Environment environment;

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
