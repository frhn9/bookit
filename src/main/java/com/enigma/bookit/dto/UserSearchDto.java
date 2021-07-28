package com.enigma.bookit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {
    private String searchUserName;
    private String searchFullName;
    private String searchGender;
    private String searchAddress;
    private String searchJob;
    private Date searchDateofBirth;
    private Date searchCreatedAt;
}
