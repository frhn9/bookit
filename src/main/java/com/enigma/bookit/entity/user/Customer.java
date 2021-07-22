package com.enigma.bookit.entity.user;

import com.enigma.bookit.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_customer")
public class Customer extends Identity {
    private String job;

}
