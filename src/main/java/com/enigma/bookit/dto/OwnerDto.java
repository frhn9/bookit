package com.enigma.bookit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private String id;
    private String userName;
    private String fullName;
    private String address;
    private String contact;
    @Email(message = "Wrong email input")
    private String email;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
}
