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
                        .version("1.0.0")
                        .description("Vex API implemented with Spring Boot RESTful service and documented using springdoc-openapi and OpenAPI 3.0.3.")
                        .contact(new Contact()
                                .email("ginosiam1998@gmail.com")
                                .name("Gino Siampichetti")
                        )
                );
    }
}
