package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class FacilityDto {
    private String id;
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "address must not be blank")
    private String address;
    @NotBlank(message = "contact must not be blank")
    private String contact;
    private Integer rentPriceOnce;
    private Integer rentPriceWeekly;
    private Integer rentPriceMonthly;
    private Boolean status;
    @NotBlank(message = "location must not be blank")
    private String location;
    private Integer capacity;
    private Category category;
}
