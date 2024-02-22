package com.ekart.userservice.service;

import com.ekart.userservice.entity.User;
import com.ekart.userservice.model.dto.request.SignInForm;
import com.ekart.userservice.model.dto.request.SignUpForm;
import com.ekart.userservice.model.dto.response.JwtResponseMessage;

import reactor.core.publisher.Mono;

public interface UserService {
    User registerUser(SignUpForm signUpFrom);

    Mono<JwtResponseMessage> login(SignInForm signInForm);
}