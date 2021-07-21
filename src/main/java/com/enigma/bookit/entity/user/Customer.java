package com.enigma.bookit.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mst_customer")
public class Customer extends Identity {
    private String job;

    public Customer(String userName, String fullName, String email, String password, LocalDateTime createdAt,
                    LocalDateTime updatedAt, LocalDateTime deletedAt, String address, String contact,
                    String gender, Date dateOfBirth, String job) {
        super(userName, fullName, email, password, createdAt, updatedAt,
                deletedAt, address, contact, gender, dateOfBirth);
        this.job = job;
    }

}
