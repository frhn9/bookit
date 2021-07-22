package com.enigma.bookit.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String fullName;
    private String address;
    private String contact;
    @Email(message = "Wrong email input")
    private String email;
    private String gender;
    private String job;
}
