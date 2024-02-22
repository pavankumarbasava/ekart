package com.ekart.userservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekart.userservice.client.CartFeignClient;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.event.RegistrationCompleteEvent;
import com.ekart.userservice.model.dto.request.SignInForm;
import com.ekart.userservice.model.dto.request.SignUpForm;
import com.ekart.userservice.model.dto.request.TokenValidationResponse;
import com.ekart.userservice.model.dto.response.JwtResponseMessage;
import com.ekart.userservice.model.dto.response.ResponseMessage;
import com.ekart.userservice.security.jwt.JwtProvider;
import com.ekart.userservice.security.validate.AuthorityTokenUtil;
import com.ekart.userservice.security.validate.TokenValidate;
import com.ekart.userservice.service.impl.UserServiceImpl;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JwtProvider jwtProvider;

//	@Autowired
//	private ApplicationEventPublisher publisher;   //	this is used for syncronous events

	@Autowired
	 private  ApplicationEventMulticaster applicationEventMulticaster;
	
	@Autowired
	 private  CartFeignClient cartFeignClient;

	@PostMapping({ "/signup", "/register" })
	public ResponseEntity<ResponseMessage> register(@Valid @RequestBody SignUpForm signUpForm,
			final HttpServletRequest request) {

		try {

			if (userService.existsByUsername(signUpForm.getUsername())) {
				return new ResponseEntity<>(new ResponseMessage(signUpForm.getUsername() + " is an existed user name"),
						HttpStatus.UNAUTHORIZED);
			}
			if (userService.existsByEmail(signUpForm.getEmail())) {
				return new ResponseEntity<>(new ResponseMessage(signUpForm.getUsername() + " is an existed email"),
						HttpStatus.UNAUTHORIZED);
			}

			User user = userService.registerUser(signUpForm);
			cartFeignClient.createCart( user.getId());
			applicationEventMulticaster.multicastEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
		} catch (Exception e) {
			logger.error("Un authorized request", e);
		}

		return new ResponseEntity<>(new ResponseMessage("Create user: " + signUpForm.getUsername() + " successfully."),
				HttpStatus.OK);

	}

	@PostMapping({ "/signin", "/login" })
	public Mono<ResponseEntity<JwtResponseMessage>> login(@Valid @RequestBody SignInForm signInForm) {
		return userService.login(signInForm).map(ResponseEntity::ok).onErrorResume(error -> {
			JwtResponseMessage errorjwtResponseMessage = new JwtResponseMessage(null, null

			);
			return Mono.just(new ResponseEntity<>(errorjwtResponseMessage, HttpStatus.UNAUTHORIZED));
		});
	}

	@PostMapping({ "/refresh", "/refresh-token" })
	public ResponseEntity<JwtResponseMessage> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
		JwtResponseMessage newRefreshToken = userService.refreshToken(refreshToken);
		return ResponseEntity.ok(newRefreshToken);
	}

	@GetMapping({ "/validateToken", "/validate-token" })
	public Boolean validateToken(@RequestHeader(name = "Authorization") String authorizationToken) {
		TokenValidate validate = new TokenValidate();
		if (validate.validateToken(authorizationToken)) {
			return ResponseEntity.ok(new TokenValidationResponse("Valid token")).hasBody();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponse("Invalid token"))
					.hasBody();
		}
	}

	// check role token authorities
	@GetMapping({ "/hasAuthority", "/authorization" })
	public Boolean getAuthority(@RequestHeader(name = "Authorization") String authorizationToken, String requiredRole) {
		AuthorityTokenUtil authorityTokenUtil = new AuthorityTokenUtil();
		List<String> authorities = authorityTokenUtil.checkPermission(authorizationToken);

		if (authorities.contains(requiredRole)) {
			return ResponseEntity.ok(new TokenValidationResponse("Role access api")).hasBody();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponse("Invalid token"))
					.hasBody();
		}
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@GetMapping("/verifyRegistration")
	public ResponseEntity<ResponseMessage> verifyRegistration(@RequestParam("token") String token) {
		String message = "Token expired. Please try again";

		try {
			jwtProvider.validateToken(token);
			userService.updateActivationFlag(true,jwtProvider.getUserNameFromToken(token));
	
			return new ResponseEntity<>(new ResponseMessage("User Verified Successfully"), HttpStatus.OK);

		} catch (Exception e) {
            logger.error(message,e);
			return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.UNAUTHORIZED);
		}

	}
	
	
}
