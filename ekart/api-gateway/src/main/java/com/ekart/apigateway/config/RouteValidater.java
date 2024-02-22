package com.ekart.apigateway.config;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidater {

	  public static final List<String> openApiEndpoints = List.of(
	            "/api/auth/",
	            "/api/manager/token",
	            "/eureka"
	    );

	    public Predicate<ServerHttpRequest> isSecured =
	            request -> openApiEndpoints
	                    .stream()
	                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

	            
}
