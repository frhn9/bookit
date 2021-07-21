package com.enigma.bookit.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OwnerDto {
    private String fullName;
    private String address;
    private String contact;
    @Email(message = "Wrong email input")
    private String email;
    private String gender;
}
