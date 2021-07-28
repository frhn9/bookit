package com.enigma.bookit.security.services.owner;

import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.security.services.customer.CustomerDetailsImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@AllArgsConstructor
public class OwnerDetailsImpl implements UserDetails {

    private String id;
    private String userName;
    @JsonIgnore
    private String password;
    private String fullName;
    private String address;
    private String contact;
    private String gender;
    private String email;
    private Date dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    private LocalDateTime deletedAt;
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDetailsImpl that = (OwnerDetailsImpl) o;
        return id.equals(that.id) &&
                userName.equals(that.userName) &&
                password.equals(that.password) &&
                fullName.equals(that.fullName) &&
                address.equals(that.address) &&
                contact.equals(that.contact) &&
                gender.equals(that.gender) &&
                email.equals(that.email) &&
                dateOfBirth.equals(that.dateOfBirth) &&
                createdAt.equals(that.createdAt) &&
                updatedAt.equals(that.updatedAt)
                && deletedAt.equals(that.deletedAt) &&
                authorities.equals(that.authorities);
    }

    public static OwnerDetailsImpl build(Owner owner){
        List<GrantedAuthority> authorities = owner.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new OwnerDetailsImpl(
                owner.getId(),
                owner.getUserName(),
                owner.getPassword(),
                owner.getFullName(),
                owner.getAddress(),
                owner.getContact(),
                owner.getGender(),
                owner.getEmail(),
                owner.getDateOfBirth(),
                owner.getCreatedAt(),
                owner.getUpdatedAt(),
                owner.getDeletedAt(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
