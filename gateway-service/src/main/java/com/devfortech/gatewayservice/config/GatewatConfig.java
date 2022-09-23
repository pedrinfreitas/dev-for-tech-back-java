package com.devfortech.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewatConfig {

    @Value("${app.authorization.url}")
    private String authorization;

    @Value("${app.crud.url}")
    private String crud;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authorization", r -> r.path("/auth/**")
                        .uri(authorization))
                .route("crud", r -> r.path("/crud/**")
                        .uri(crud))
                .build();
    }

}
