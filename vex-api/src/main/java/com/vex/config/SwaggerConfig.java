package com.vex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Vex API")
                        .termsOfService("http://swagger.io/terms/")
                        .version("1.0.0")
                        .description("Vex API microservice with Spring Boot using WebFlux to handle requests and responses.")
                        .contact(new Contact()
                                .email("ginosiam1998@gmail.com")
                                .name("Gino Siampichetti")
                        )
                );
    }
}
