package com.enigma.bookit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class FacilitySearchDto {
    private String searchFacilityName;
    private String searchFacilityLocation;
    private BigDecimal searchFacilityRentPriceOnce;
    private BigDecimal searchFacilityRentPriceMonthly;
    private BigDecimal searchFacilityRentPriceWeekly;

    public FacilitySearchDto(String searchFacilityName, String searchFacilityLocation, BigDecimal searchFacilityRentPriceOnce, BigDecimal searchFacilityRentPriceMonthly, BigDecimal searchFacilityRentPriceWeekly) {
        this.searchFacilityName = searchFacilityName;
        this.searchFacilityLocation = searchFacilityLocation;
        this.searchFacilityRentPriceOnce = searchFacilityRentPriceOnce;
        this.searchFacilityRentPriceMonthly = searchFacilityRentPriceMonthly;
        this.searchFacilityRentPriceWeekly = searchFacilityRentPriceWeekly;
    }
}
