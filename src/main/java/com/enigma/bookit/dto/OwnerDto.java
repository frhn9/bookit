package com.enigma.bookit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OwnerDto {
    private String id;
    @NotBlank (message = "Full Name must not be blank")
    private String fullName;
    @NotBlank (message = "Address must not be blank")
    private String address;
    @NotBlank (message = "Contact must not be blank")
    private String contact;
    @NotBlank (message = "Email must not be blank")
    @Email(message = "Wrong email input")
    private String email;
    @NotBlank (message = "Gender must not be blank")
    private String gender;
}
