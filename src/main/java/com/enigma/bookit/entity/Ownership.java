package com.enigma.bookit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "trx_ownership")
public class Ownership {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties("ownership")
    private Owner owner;

    public Ownership(String id, Owner owner) {
        this.id = id;
        this.owner = owner;
    }
}
