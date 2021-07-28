package com.enigma.bookit.dto.request;

import com.enigma.bookit.entity.Category;
import com.enigma.bookit.entity.Files;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter @Setter @NoArgsConstructor
public class UpdateFacilityRequest {
    private String id;
    private String name;
    private String address;
    private String contact;
    private BigDecimal rentPriceOnce;
    private BigDecimal rentPriceWeekly;
    private BigDecimal rentPriceMonthly;
    private Boolean status;
    private String location;
    private Integer capacity;
    private Category category;
    private Files file;

    public UpdateFacilityRequest(String id, String name, String address, String contact, BigDecimal rentPriceOnce, BigDecimal rentPriceWeekly, BigDecimal rentPriceMonthly, Boolean status, String location, Integer capacity, Category category) {
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
