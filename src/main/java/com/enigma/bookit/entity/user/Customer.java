package com.enigma.bookit.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_customer")
public class Customer extends Identity {
    private String job;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name = "customer_roles",
                joinColumns = @JoinColumn (name = "customer_id"),
                inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Customer(String fullName, String userName, String password, String email){
        this.setFullName(fullName);
        this.setUserName(userName);
        this.setPassword(password);
        this.setEmail(email);
    }
}
