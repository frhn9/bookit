package com.enigma.bookit.security.payload.response.owner;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class OwnerJwtResponse {
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
    private Date dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<String> roles;

    public OwnerJwtResponse(String token, String id, String userName, String fullName, String address, String contact, String gender, String email, Date dateOfBirth, List<String> roles) {
        this.token = token;
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.roles = roles;
    }
}
