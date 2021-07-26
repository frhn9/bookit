package com.enigma.bookit.specification;

import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.Owner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OwnerSpecification {

    public static Specification<Owner> getSpecification(UserSearchDto userSearchDto){
        return new Specification<Owner>() {
            @Override
            public Predicate toPredicate(Root<Owner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(!(userSearchDto.getSearchUserName() == null)){
                    Predicate customerUserNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("userName")),
                            "%"+userSearchDto.getSearchUserName().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(customerUserNamePredicate);
                }

                if(!(userSearchDto.getSearchFullName() == null)){
                    Predicate customerFullNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
                            "%"+userSearchDto.getSearchFullName().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(customerFullNamePredicate);
                }

                if(!(userSearchDto.getSearchGender() == null)){
                    Predicate customerGenderPredicate = criteriaBuilder.equal(root.get("gender"),
                            "%"+userSearchDto.getSearchGender().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(customerGenderPredicate);
                }

                if(!(userSearchDto.getSearchAddress() == null)){
                    Predicate customerAddressPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("address")),
                            "%"+userSearchDto.getSearchAddress().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(customerAddressPredicate);
                }

                if(!(userSearchDto.getSearchDateofBirth() == null)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String modifiedDateFormatted = sdf.format(userSearchDto.getSearchDateofBirth());

                    Predicate customerDateOfBirthPredicate = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR", String.class, root.get("dateOfBirth"),
                            criteriaBuilder.literal("yyyy-MM-dd")), modifiedDateFormatted);
                    predicates.add(customerDateOfBirthPredicate);
                }

                if(!(userSearchDto.getSearchCreatedAt() == null)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String modifiedDateFormatted = sdf.format(userSearchDto.getSearchCreatedAt());

                    Predicate customerDateOfBirthPredicate = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR", String.class, root.get("createdAt"),
                            criteriaBuilder.literal("yyyy-MM-dd")), modifiedDateFormatted);
                    predicates.add(customerDateOfBirthPredicate);
                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }


}
