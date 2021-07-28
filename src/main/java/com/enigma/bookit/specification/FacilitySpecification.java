package com.enigma.bookit.specification;

import com.enigma.bookit.dto.FacilitySearchDto;
import com.enigma.bookit.entity.Facility;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FacilitySpecification {

    public static Specification<Facility> getSpesification(FacilitySearchDto facilitySearchDto){
        return new Specification<Facility>() {
            @Override
            public Predicate toPredicate(Root<Facility> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(facilitySearchDto.getSearchFacilityName() != null){
                    Predicate facilityNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%"  +facilitySearchDto.getSearchFacilityName().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(facilityNamePredicate);
                }
                if(facilitySearchDto.getSearchFacilityLocation() != null){
                    Predicate facilityLocationPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("location")),
                            "%"  +facilitySearchDto.getSearchFacilityLocation().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(facilityLocationPredicate);
                }
                if(facilitySearchDto.getSearchFacilityRentPriceOnce() != null){
                    Predicate facilityRentPriceOncePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("rentPriceOnce"),
                            facilitySearchDto.getSearchFacilityRentPriceOnce());
                    predicates.add(facilityRentPriceOncePredicate);
                }
                if(facilitySearchDto.getSearchFacilityRentPriceWeekly() != null){
                    Predicate facilityRentPriceWeeklyPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("rentPriceWeekly"),
                            facilitySearchDto.getSearchFacilityRentPriceWeekly());
                    predicates.add(facilityRentPriceWeeklyPredicate);
                }
                if(facilitySearchDto.getSearchFacilityRentPriceMonthly() != null){
                    Predicate facilityRentPriceMonthlyPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("rentPriceMonthly"),
                            facilitySearchDto.getSearchFacilityRentPriceMonthly());
                    predicates.add(facilityRentPriceMonthlyPredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
