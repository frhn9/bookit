package com.enigma.bookit.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

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
    private BigDecimal rentPriceOnce;
    @Column(name="rent_price_weekly")
    private BigDecimal rentPriceWeekly;
    @Column(name="rent_price_monthly")
    private BigDecimal rentPriceMonthly;
    private Boolean status;
    private String location;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "files_id")
    private Files files;

    public Facility(String id, String name, String address, String contact, BigDecimal rentPriceOnce, BigDecimal rentPriceWeekly, BigDecimal rentPriceMonthly, Boolean status, String location, Integer capacity, Category category) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.rentPriceOnce = rentPriceOnce;
        this.rentPriceWeekly = rentPriceWeekly;
        this.rentPriceMonthly = rentPriceMonthly;
        this.status = status;
        this.location = location;
        this.capacity = capacity;
        this.category = category;
    }

    public Facility(String id, String name, String address, String contact, BigDecimal rentPriceOnce, BigDecimal rentPriceWeekly, BigDecimal rentPriceMonthly, Boolean status, String location, Integer capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.rentPriceOnce = rentPriceOnce;
        this.rentPriceWeekly = rentPriceWeekly;
        this.rentPriceMonthly = rentPriceMonthly;
        this.status = status;
        this.location = location;
        this.capacity = capacity;
        this.category = category;
    }
}

