package com.example.bookit.dto;

import com.example.bookit.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Getter @Setter @NoArgsConstructor
public class FacilityDto {
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
    private Boolean status;
    @NotBlank(message = "location must not be blank")
    private String location;
    private Category category;
}
