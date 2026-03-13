package com.prashant.backendorderservice.order.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API",
                version = "1.0",
                description = "REST APIs for managing order lifecycle"
        ),
        servers = @Server(
                url = "http://localhost:8082",
                description = "Local Development Server"
        )
)
public class OpenApiConfig {
}
