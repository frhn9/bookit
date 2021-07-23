package com.enigma.bookit.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotBlank(message = "Username must not be blank")
    private String userName;
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Wrong email input")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

}
