package com.enigma.bookit.security.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank (message = "Please input your username")
    private String userName;
    @NotBlank (message = "Please input your password")
    private String password;
}
