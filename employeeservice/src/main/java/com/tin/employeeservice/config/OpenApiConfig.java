package com.tin.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Api Specification - VVT",
                description = "Api Documentation for Employee Service",
                version = "1.0",
                contact = @Contact(
                        name = "Van Tin",
                        email = "tintran2712@gmail.com"
                )
        ),
        servers = {
                @Server(
                    description = "Local ENV",
                        url = "http://localhost:9002"
                ),
                @Server(
                    description = "Prod ENV",
                    url = "http://localhost:9002"
                )
        }
)
public class OpenApiConfig {
}
