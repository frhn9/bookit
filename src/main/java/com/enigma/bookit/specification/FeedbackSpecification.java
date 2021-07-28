package com.enigma.bookit.specification;

import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackSpecification {

    public static Specification<Feedback> getSpecification(FeedbackSearchDTO feedbackSearchDTO){
        return new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!(feedbackSearchDTO.getBook() == null)){
                    Predicate book = criteriaBuilder.like(root.get("bookId").get("id"), feedbackSearchDTO.getBook().getId());
                    predicates.add(book);
                }
                if(!(feedbackSearchDTO.getFeedback() == null)){
                    Predicate feedback = criteriaBuilder.like(criteriaBuilder.lower(root.get("feedback")), "%"+ feedbackSearchDTO.getFeedback().toLowerCase() +"%");
                    predicates.add(feedback);
                }
                if(!(feedbackSearchDTO.getResponse() == null)){
                    Predicate response = criteriaBuilder.like(criteriaBuilder.lower(root.get("response")), "%"+feedbackSearchDTO.getResponse().toLowerCase()+"%");
                    predicates.add(response);
                }
                if(!(feedbackSearchDTO.getRatingMore() == null)){
                    Predicate ratingMore = criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), feedbackSearchDTO.getRatingMore());
                    predicates.add(ratingMore);
                }
                if(!(feedbackSearchDTO.getRatingLess()==null)){
                    Predicate ratingLess = criteriaBuilder.lessThanOrEqualTo(root.get("rating"), feedbackSearchDTO.getRatingLess());
                    predicates.add(ratingLess);
                }
                if(!(feedbackSearchDTO.getFacilityName() == null)){
                    Predicate facilityName = criteriaBuilder.like(criteriaBuilder.lower(root.get("book").get("payment").get("facility").get("name")),
                            "%" + feedbackSearchDTO.getFacilityName().toLowerCase() + "%");
                    predicates.add(facilityName);
                }
                if(!(feedbackSearchDTO.getCustomerName() == null)) {
                    Predicate customerName = criteriaBuilder.like(criteriaBuilder.lower(root.get("book").get("payment").get("customer").get("fullName"))
                            , "%" + feedbackSearchDTO.getCustomerName().toLowerCase() + "%");
                    predicates.add(customerName);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
