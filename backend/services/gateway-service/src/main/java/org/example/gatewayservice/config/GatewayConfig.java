package org.example.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.example.gatewayservice.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("address-service", r -> r.path("/api/address/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://address-service"))
                .route("user-service", r -> r.path("/api/auth/**")
                        .uri("lb://user-service"))
                .build();
    }
}
