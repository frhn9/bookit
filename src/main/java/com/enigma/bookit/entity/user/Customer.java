package com.enigma.bookit.entity.user;

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
