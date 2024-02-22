package com.ekart.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ekart.userservice.entity.User;
import com.ekart.userservice.model.dto.model.TokenManager;
import com.ekart.userservice.repository.UserRepository;
import com.ekart.userservice.service.UserService;

@Service
@RequestMapping("/api/manager")
public class SenToken {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenManager tokenManager;

    
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/token/{username}")
    public ResponseEntity<String> getTokenByUsername(@PathVariable("username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        String token = null;
        if (user != null) {
            token = tokenManager.getTokenByUsername(username);
        }

        return (token != null)
                ? ResponseEntity.ok(token)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found for the username.");
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken() {
        String token = tokenManager.TOKEN;
        return (token != null)
                ? ResponseEntity.ok(token)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found for the username.");
    }

}
