package com.set.image_service_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Image Service API",
        version = "1.0.0",
        description = "Simple API entry point for uploading image files and retrieving image metadata by label.",
        contact = @Contact(
            name = "SET",
            email = "support@example.com"
        ),
        license = @License(
            name = "Internal Use"
        )
    ),
    servers = {
        @Server(url = "/", description = "Default server")
    }
)
public class OpenApiConfig {
}
