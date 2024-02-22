package com.ekart.apigateway.security.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class GetUserNameFromToken {

//  @Value("${jwt.secret}")
	private static String jwtSecret = "vip2023";

	public static String getUserNameFromToken(String token) throws Exception {
		try {

			Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret) // Thêm khóa bí mật khi giải mã token
					.parseClaimsJws(token);

			return claims.getBody().getSubject();

		} catch (Exception e) {
			throw new Exception("Unable to extract username from token ", e);
		}
	}
}
