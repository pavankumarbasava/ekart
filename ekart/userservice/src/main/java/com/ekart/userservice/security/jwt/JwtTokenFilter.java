package com.ekart.userservice.security.jwt;

import java.io.IOException;

import javax.persistence.ManyToMany;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ekart.userservice.security.userprinciple.UserDetailService;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserDetailService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

	
		
		try {
			String token = getJwtToken(request);
			if (token != null && jwtProvider.validateToken(token)) {
				String username = jwtProvider.getUserNameFromToken(token);
				UserDetails userDetails = userDetailService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

				// Tạo mới refresh token
				String refreshToken = jwtProvider.createRefreshToken(authenticationToken);

				// Gửi cả token và refresh token về cho người dùng
				response.setHeader("Authorization", "Bearer " + token);
				response.setHeader("Refresh-Token", refreshToken);
			}

		} catch (Exception e) {
			  logger.error("Can't set user authentication -> Message: ", e);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtToken(HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer")) {
			return token.replace("Bearer ", "");
		}
		return null;
	}

}
