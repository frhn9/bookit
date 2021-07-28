package com.enigma.bookit.security.services.customer;

import com.enigma.bookit.entity.user.Customer;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Getter
@AllArgsConstructor
public class CustomerDetailsImpl implements UserDetails {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDetailsImpl that = (CustomerDetailsImpl) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contact, that.contact) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(email, that.email)
                && Objects.equals(job, that.job)
                && Objects.equals(dateOfBirth, that.dateOfBirth)
                && Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(deletedAt, that.deletedAt) &&
                Objects.equals(authorities, that.authorities);
    }

    public static CustomerDetailsImpl build(Customer customer){
        List<GrantedAuthority> authorities = customer.getRoles().stream()
                                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                        .collect(Collectors.toList());

        return new CustomerDetailsImpl(
                customer.getId(),
                customer.getUserName(),
                customer.getPassword(),
                customer.getFullName(),
                customer.getAddress(),
                customer.getContact(),
                customer.getGender(),
                customer.getEmail(),
                customer.getJob(),
                customer.getDateOfBirth(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getDeletedAt(),
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
