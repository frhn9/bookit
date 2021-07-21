package com.enigma.bookit.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public class Identity extends User {
    private String address;
    private String contact;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    public Identity(String userName, String fullName, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                    String address, String contact, String gender, Date dateOfBirth) {
        super(userName, fullName, email, password, createdAt, updatedAt, deletedAt);
        this.address = address;
        this.contact = contact;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
