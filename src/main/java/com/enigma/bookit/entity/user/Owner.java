package com.enigma.bookit.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_owner")
public class Owner extends Identity {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name = "owner_roles",
                joinColumns = @JoinColumn (name = "owner_id"),
                inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Owner(String fullName, String userName, String password, String email){
        this.setFullName(fullName);
        this.setUserName(userName);
        this.setPassword(password);
        this.setEmail(email);
    }
}
