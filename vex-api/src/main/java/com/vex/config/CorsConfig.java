package com.vex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;


@Configuration
@EnableWebFlux
public class CorsConfig implements WebFluxConfigurer {

    @Value("${vex.gateway}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(org.springframework.web.reactive.config.CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
//            .exposedHeaders("Vex-Application") // can expose headers to the client or use in other microservices
            .maxAge(3600);
    }

}
