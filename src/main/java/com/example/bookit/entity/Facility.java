package com.example.bookit.entity;


import com.example.bookit.constant.ResponseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Entity
@Getter @Setter @NoArgsConstructor
@Table (name="mst_facility")
public class Facility {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name="facility_id")
    private String id;
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "address must not be blank")
    private String address;
    @NotBlank(message = "contact must not be blank")
    private String contact;
    private BigInteger rent_price_once;
    private BigInteger rent_price_weekly;
    private BigInteger rent_price_monthly;
    @NotBlank(message = "status must not be blank")
    private Boolean status;
    @NotBlank(message = "location must not be blank")
    private String location;
    private BigInteger balance;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;


    public Facility(String id, String name, String address, String contact, BigInteger rent_price_once, BigInteger rent_price_weekly, BigInteger rent_price_monthly, Boolean status, String location, BigInteger balance, Category category) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.rent_price_once = rent_price_once;
        this.rent_price_weekly = rent_price_weekly;
        this.rent_price_monthly = rent_price_monthly;
        this.status = status;
        this.location = location;
        this.balance = balance;
        this.category = category;
    }
}
