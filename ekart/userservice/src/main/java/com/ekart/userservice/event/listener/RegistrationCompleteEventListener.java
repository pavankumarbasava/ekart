package com.ekart.userservice.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ekart.userservice.constants.ConfigConstants;
import com.ekart.userservice.entity.User;
import com.ekart.userservice.event.RegistrationCompleteEvent;
import com.ekart.userservice.model.dto.request.EmailDetails;
import com.ekart.userservice.security.jwt.JwtProvider;
import com.ekart.userservice.security.userprinciple.UserPrinciple;
import com.ekart.userservice.service.impl.UserServiceImpl;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationCompleteEventListener.class);

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	 private  RestTemplate restTemplate;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {

		User user = event.getUser();
		 UserPrinciple userPrinciple = UserPrinciple.build(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userPrinciple, userPrinciple.getPassword(), userPrinciple.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.createRefreshToken(authentication);

		// send mail to user

		String url = event.getApplicationUrl() + "/api/auth/verifyRegistration?token=" + token;
		EmailDetails emailDetails = new EmailDetails(user.getEmail(),url,"Thanks for registration with ekart please activate your account by clicking this link",null);
		Boolean flag = restTemplate
                .postForObject(ConfigConstants.DiscoveredDomainsApi
                        .MAIL_SERVICE_API_URL + "/" + "sendMail",emailDetails, Boolean.class);
		
		logger.info("Click the link to verify your account: {} and emial flag:{} " , url,flag);

	}

}
