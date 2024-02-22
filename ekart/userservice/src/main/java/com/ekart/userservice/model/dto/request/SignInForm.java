package com.ekart.userservice.model.dto.request;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class SignInForm {
    private String usernameOrEmail;
    private String password;
}
