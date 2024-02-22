package com.ekart.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.ekart.apigateway.exception.UnauthorisedException;
import com.ekart.apigateway.security.jwt.Authorities;
import com.ekart.apigateway.security.jwt.GetUserNameFromToken;
import com.ekart.apigateway.security.jwt.TokenValidate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidater validator;

    @Autowired
    private Authorities authorities;

    @Autowired
    private TokenValidate jwtUtil;
    
    @Autowired
    private GetUserNameFromToken getUserNameService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) { 
      
    	return ((exchange, chain) -> {
        	 ServerHttpRequest serverHttpRequest=null;
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                 
                	jwtUtil.validateToken(authHeader);
                	
                	serverHttpRequest = exchange.getRequest()
                    .mutate()
                    .header("User", getUserNameService.getUserNameFromToken(authHeader))
                    .header("UserAuthorities", authorities.extractAuthoritiesFromToken(authHeader).toString())
                    .build();
                    
                    
//                    .mutate()
//                    
//                    
//                    .header("user",getUserNameService.getUserNameFromToken(authHeader)).build();

                } catch (Exception e) {
                   // System.out.println("invalid access...!");
                   // throw new RuntimeException("Unauthorized access to application");
                	  throw new UnauthorisedException(HttpStatus.FORBIDDEN, "Not Authorised from Gateway");
                      
                }
                return chain.filter(exchange.mutate().request(serverHttpRequest).build()); 
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
 

}
