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
    private String name;
    private String address;
    private String contact;
    @Column(name="rent_price_once")
    private BigInteger rentPriceOnce;
    @Column(name="rent_price_weekly")
    private BigInteger rentPriceWeekly;
    @Column(name="rent_price_monthly")
    private BigInteger rentPriceMonthly;
    private Boolean status;
    private String location;
    private BigInteger balance;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;


    public Facility(String name, String address, String contact, BigInteger rentPriceOnce, BigInteger rentPriceWeekly, BigInteger rentPriceMonthly, Boolean status, String location, BigInteger balance, Category category) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.rentPriceOnce = rentPriceOnce;
        this.rentPriceWeekly = rentPriceWeekly;
        this.rentPriceMonthly = rentPriceMonthly;
        this.status = status;
        this.location = location;
        this.balance = balance;
        this.category = category;
    }
}
