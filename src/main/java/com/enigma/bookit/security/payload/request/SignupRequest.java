package com.enigma.bookit.security.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;
    @NotBlank(message = "Username cannot be empty")
    private String userName;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should have minimum 8 characters")
    private String password;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Wrong email input")
    private String email;
    private Set<String> role;
}
