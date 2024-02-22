package com.ekart.apigateway;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

import com.ekart.apigateway.config.AuthenticationFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableWebFlux
public class ApiGatewayApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("IST +0:00"));
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
//
	@Bean
	   public RouteLocator routeLocator(RouteLocatorBuilder rlb, AuthenticationFilter
	   authorizationHeaderFilter) {

	       return rlb
	               .routes()
	               .route(p -> p
	                   .path("/api/product/**")
	                   .filters(f -> f.removeRequestHeader("Cookie")
	                           .rewritePath("/api/product/(?<segment>.*)", "/api/product/$\\{segment}")
	                           .filter(authorizationHeaderFilter.apply(new 
	                        		   AuthenticationFilter.Config())))
	                .uri("lb://product-service"))
	               
//	                .route(p -> p
//	 	                   .path("/api/auth/**")
//	 	                   .filters(f -> f.removeRequestHeader("Cookie")
//	 	                           .rewritePath("/api/auth/(?<segment>.*)", "/api/auth/$\\{segment}")
//	 	                           .filter(authorizationHeaderFilter.apply(new 
//	 	                        		   AuthenticationFilter.Config())))
//	 	               .uri("lb://user-service"))
	               
	               .route(p -> p
	 	                   .path("/api/auth/**")
	 	                   .filters(f -> f.removeRequestHeader("Cookie")
	 	                           .rewritePath("/api/auth/(?<segment>.*)", "/api/auth/$\\{segment}")
	 	                           .filter(authorizationHeaderFilter.apply(new 
	 	                        		   AuthenticationFilter.Config())))
	 	               .uri("lb://user-service"))
	               
	               .route(p -> p
	 	                   .path("/api/information/**")
	 	                   .filters(f -> f.removeRequestHeader("Cookie")
	 	                           .rewritePath("/api/information/(?<segment>.*)", "/api/information/$\\{segment}")
	 	                           .filter(authorizationHeaderFilter.apply(new 
	 	                        		   AuthenticationFilter.Config())))
	 	               .uri("lb://user-service"))
	              
	               .route(p -> p
	 	                   .path("/api/product/**")
	 	                   .filters(f -> f.removeRequestHeader("Cookie")
	 	                           .rewritePath("/api/product/(?<segment>.*)", "/api/product/$\\{segment}")
	 	                           .filter(authorizationHeaderFilter.apply(new 
	 	                        		   AuthenticationFilter.Config())))
	 	               .uri("lb://product-service"))
	               .route(p -> p
	 	                   .path("/api/order/**")
	 	                   .filters(f -> f.removeRequestHeader("Cookie")
	 	                           .rewritePath("/api/order/(?<segment>.*)", "/api/order/$\\{segment}")
	 	                           .filter(authorizationHeaderFilter.apply(new 
	 	                        		   AuthenticationFilter.Config())))
	 	               .uri("lb://order-service"))
	                 .route(p -> p
	 	                   .path("/api/cart/**")
	 	                   .filters(f -> f.removeRequestHeader("Cookie")
	 	                           .rewritePath("/api/cart/(?<segment>.*)", "/api/cart/$\\{segment}")
	 	                           .filter(authorizationHeaderFilter.apply(new 
	 	                        		   AuthenticationFilter.Config())))
	 	               .uri("lb://cart-service"))
	                 .route(p -> p
		 	                   .path("/api/payments/**")
		 	                   .filters(f -> f.removeRequestHeader("Cookie")
		 	                           .rewritePath("/api/payments/(?<segment>.*)", "/api/payments/$\\{segment}")
		 	                           .filter(authorizationHeaderFilter.apply(new 
		 	                        		   AuthenticationFilter.Config())))
		 	               .uri("lb://payments-service"))
	                 .route(p -> p
		 	                   .path("/api/inventory/**")
		 	                   .filters(f -> f.removeRequestHeader("Cookie")
		 	                           .rewritePath("/api/inventory/(?<segment>.*)", "/api/inventory/$\\{segment}")
		 	                           .filter(authorizationHeaderFilter.apply(new 
		 	                        		   AuthenticationFilter.Config())))
		 	               .uri("lb://inventory-service"))
	                
	            .build();
	     }
}
