package com.enigma.bookit.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    private Integer rentPriceOnce;
    @Column(name="rent_price_weekly")
    private Integer rentPriceWeekly;
    @Column(name="rent_price_monthly")
    private Integer rentPriceMonthly;
    private Boolean status;
    private String location;
    private Integer balance;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;


    public Facility(String id, String name, String address, String contact, Integer rentPriceOnce, Integer rentPriceWeekly, Integer rentPriceMonthly, Boolean status, String location, Integer balance, Category category) {
        this.id = id;
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
