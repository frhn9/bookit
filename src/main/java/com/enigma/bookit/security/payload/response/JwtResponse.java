package com.enigma.bookit.security.payload.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String userName;
    private String password;
    private String fullName;
    private String address;
    private String contact;
    private String gender;
    private String email;
    private String job;
    private Date dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<String> roles;

    public JwtResponse(String token, String id, String userName, String fullName, String address, String contact, String gender, String email, String job, Date dateOfBirth, List<String> roles) {
        this.token = token;
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.email = email;
        this.job = job;
        this.dateOfBirth = dateOfBirth;
        this.roles = roles;
    }
}
