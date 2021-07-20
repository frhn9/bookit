package com.example.bookit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Getter @Setter @NoArgsConstructor
public class FacilityDto {
    private String id;
    private String name;
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
}
