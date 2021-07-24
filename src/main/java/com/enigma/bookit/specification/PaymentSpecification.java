package com.enigma.bookit.specification;

import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {
    public static Specification<Payment> getSpecification(PaymentSearchDTO paymentSearchDTO){
        return new Specification<Payment>() {
            @Override
            public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!(paymentSearchDTO.getCustomerName() == null)){
                    Predicate customerName = criteriaBuilder.like(criteriaBuilder.lower(root.get("customer").get("fullName")),
                            "%"+paymentSearchDTO.getCustomerName().toLowerCase() +"%");
                    predicates.add(customerName);
                }
                if(!(paymentSearchDTO.getFacilityName() == null)){
                    Predicate facilityName = criteriaBuilder.like(criteriaBuilder.lower(root.get("facility").get("name")),
                            "%"+paymentSearchDTO.getFacilityName().toLowerCase() +"%");
                    predicates.add(facilityName);
                }
                if(!(paymentSearchDTO.getPackageChosen() == null)){
                    Predicate customerName = criteriaBuilder.like(root.get("packageChosen").as(String.class),
                            paymentSearchDTO.getPackageChosen().toString().toUpperCase());
                    predicates.add((customerName));
                }
                if(!(paymentSearchDTO.getPaymentStatus() == null)){
                    Predicate paymentStatus = criteriaBuilder.equal(root.get("paymentStatus"), paymentSearchDTO.getPaymentStatus());
                    predicates.add(paymentStatus);
                }
                if (!(paymentSearchDTO.getAmountStart() == null)){
                    Predicate amountStart = criteriaBuilder.greaterThanOrEqualTo(root.get("refundAmount"), paymentSearchDTO.getAmountStart());
                    predicates.add(amountStart);
                }
                if (!(paymentSearchDTO.getAmountStop() == null)) {
                    Predicate amountStop = criteriaBuilder.lessThanOrEqualTo(root.get("refundAmount"), paymentSearchDTO.getAmountStop());
                    predicates.add(amountStop);
                }
                if(!(paymentSearchDTO.getBookingStartFrom() == null)){
                    Predicate bookingStartFrom = criteriaBuilder.greaterThanOrEqualTo(root.get("bookingStart"), paymentSearchDTO.getBookingStartFrom());
                    predicates.add(bookingStartFrom);
                }
                if(!(paymentSearchDTO.getBookingStartUntil() == null)){
                    Predicate bookingStartUntil = criteriaBuilder.lessThanOrEqualTo(root.get("bookingStart"), paymentSearchDTO.getBookingStartUntil());
                    predicates.add(bookingStartUntil);
                }
                if(!(paymentSearchDTO.getBookingEndFrom() == null)){
                    Predicate bookingStartFrom = criteriaBuilder.greaterThanOrEqualTo(root.get("bookingEnd"), paymentSearchDTO.getBookingEndFrom());
                    predicates.add(bookingStartFrom);
                }
                if(!(paymentSearchDTO.getBookingEndUntil() == null)){
                    Predicate bookingEndUntil = criteriaBuilder.lessThanOrEqualTo(root.get("bookingEnd"), paymentSearchDTO.getBookingEndUntil());
                    predicates.add(bookingEndUntil);
                }
                if(!(paymentSearchDTO.getDueTimeEnd() == null)){
                    Predicate dueTimeStart = criteriaBuilder.greaterThanOrEqualTo(root.get("dueTime"), paymentSearchDTO.getDueTimeStart());
                    predicates.add(dueTimeStart);
                }
                if(!(paymentSearchDTO.getDueTimeEnd() == null)){
                    Predicate dueTimeEnd = criteriaBuilder.lessThanOrEqualTo(root.get("dueTime"), paymentSearchDTO.getDueTimeEnd());
                    predicates.add(dueTimeEnd);
                }
                if(!(paymentSearchDTO.getPaymentDateFrom() == null)){
                    Predicate paymentDateFrom = criteriaBuilder.greaterThanOrEqualTo(root.get("paymentDate"), paymentSearchDTO.getPaymentDateFrom());
                    predicates.add(paymentDateFrom);
                }
                if(!(paymentSearchDTO.getPaymentDateUntil() == null)){
                    Predicate paymentDateUntil = criteriaBuilder.lessThanOrEqualTo(root.get("paymentDate"), paymentSearchDTO.getPaymentDateUntil());
                    predicates.add(paymentDateUntil);
                }
                Predicate[] arrayPredicate = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicate);
            }
        };
    }
}
