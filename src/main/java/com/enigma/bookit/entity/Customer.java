package com.example.bookit.entity;

import javax.persistence.*;

@Entity
@Table (name = "mst_customer")
public class Customer {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id")
    private String id;
    private String firstName;



}
