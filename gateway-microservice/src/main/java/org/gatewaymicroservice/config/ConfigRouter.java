package org.gatewaymicroservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigRouter {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/users/**")
                        .uri("lb://USER-MICROSERVICE"))
                .route(r -> r
                        .path("/tasks/**")
                        .uri("lb://TASK-MICROSERVICE"))
                .build();
    }

}
