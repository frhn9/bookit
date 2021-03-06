package com.enigma.bookit.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="mst_category")
public class Category {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name="category_id")
    private String id;
    @NotBlank(message ="name must not be blank")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List <Facility> facility = new ArrayList<>();

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
