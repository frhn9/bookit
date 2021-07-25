package com.enigma.bookit.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Identity extends User {
    private String address;
    private String contact;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private  Date dateOfBirth;

}
