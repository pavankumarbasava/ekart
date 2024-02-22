package com.ekart.apigateway.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class TokenValidate {

	@Value("${jwt.secret}")
	private String SECRET_KEY; // = "vip2023";

	public boolean validateToken(String token) {
		if (SECRET_KEY == null || SECRET_KEY.isEmpty())
			throw new IllegalArgumentException("Not found secret key in structure");

		if (token.startsWith("Bearer "))
			token = token.replace("Bearer ", "");

		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

			// Kiểm tra thời gian hết hạn của token
			long currentTimeMillis = System.currentTimeMillis();

			// Token đã hết hạn
			return claims.getExpiration().getTime() >= currentTimeMillis;

		} catch (ExpiredJwtException ex) {
			throw new IllegalArgumentException("Token has expired.");
		
		} catch (MalformedJwtException ex) {
			throw new IllegalArgumentException("Invalid token.");
		
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Token validation error: " + ex.getMessage());
		}
	}

}
