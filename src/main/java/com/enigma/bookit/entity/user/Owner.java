package com.enigma.bookit.entity.user;

import com.enigma.bookit.entity.user.Identity;
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
@Table(name = "mst_owner")
public class Owner extends Identity {

    public Owner(String userName, String fullName, String email, String password,
                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                 String address, String contact, String gender, Date dateOfBirth) {
        super(userName, fullName, email, password, createdAt, updatedAt,
                deletedAt, address, contact, gender, dateOfBirth);
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public String getUserName(){
//        return userName;
//    }
}
