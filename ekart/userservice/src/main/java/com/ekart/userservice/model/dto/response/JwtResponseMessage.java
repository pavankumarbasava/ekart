package com.ekart.userservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseMessage {
    private String accessToken;
    private String refreshToken;
    //private InformationMessage information;
}
