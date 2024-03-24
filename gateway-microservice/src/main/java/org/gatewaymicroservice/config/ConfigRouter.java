package org.gatewaymicroservice.config;

import lombok.AllArgsConstructor;
import org.gatewaymicroservice.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ConfigRouter {
    private final JwtAuthenticationFilter filter;
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-microservice", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-microservice"))

                .route("task-microservice", r -> r.path("/tasks/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://task-microservice"))

                .route("security-microservice", r -> r.path("/auth/**")
                        .uri("lb://security-microservice"))

                .build();
    }

}
