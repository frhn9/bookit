package com.enigma.bookit.entity.user;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(name = "mst_owner")
public class Owner extends Identity {

}
