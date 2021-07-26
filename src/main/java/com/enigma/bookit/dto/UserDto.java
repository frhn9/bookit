package com.enigma.bookit.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String userName;
    private String fullName;
    private String email;
}
