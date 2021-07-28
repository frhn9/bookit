package com.enigma.bookit.security.services;

import com.enigma.bookit.entity.user.User;
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
public class UserDetailsImpl implements UserDetails {

    private String id;
    private String userName;
    @JsonIgnore
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
    @JsonIgnore
    private LocalDateTime deletedAt;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                        .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFullName(),
                user.getAddress(),
                user.getContact(),
                user.getGender(),
                user.getEmail(),
                user.getJob(),
                user.getDateOfBirth(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
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
