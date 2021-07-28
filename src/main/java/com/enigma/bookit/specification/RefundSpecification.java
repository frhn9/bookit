package com.enigma.bookit.specification;

import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Refund;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RefundSpecification {
    public static Specification<Refund> getSpecification(RefundSearchDTO refundSearchDTO){
        return new Specification<Refund>() {
            @Override
            public Predicate toPredicate(Root<Refund> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!(refundSearchDTO.getBook() == null)){
                    Predicate book = criteriaBuilder.like(root.get("book").get("id"), refundSearchDTO.getBook().getId());
                    predicates.add(book);
                }
                if(!(refundSearchDTO.getRequestRefundTimeStart() == null)){
                    Predicate refundReqStart = criteriaBuilder.greaterThanOrEqualTo(root.get("requestRefundTime"), refundSearchDTO.getRequestRefundTimeStart());
                    predicates.add(refundReqStart);
                }
                if(!(refundSearchDTO.getRequestRefundTimeStop() == null)){
                    Predicate refundReqStop = criteriaBuilder.lessThanOrEqualTo(root.get("requestRefundTime"), refundSearchDTO.getRequestRefundTimeStop());;
                    predicates.add(refundReqStop);
                }
                if(!(refundSearchDTO.getRefundTimeStart() == null)){
                    Predicate refundStart = criteriaBuilder.greaterThanOrEqualTo(root.get("refundTime"), refundSearchDTO.getRefundTimeStart());
                    predicates.add(refundStart);
                }
                if(!(refundSearchDTO.getRefundTimeStop() == null)){
                    Predicate refundStop = criteriaBuilder.lessThanOrEqualTo(root.get("refundTime"), refundSearchDTO.getRefundTimeStop());
                    predicates.add(refundStop);
                }
                if(!(refundSearchDTO.getStatus() == null)){
                    Predicate status = criteriaBuilder.equal(root.get("status"), refundSearchDTO.getStatus());
                    predicates.add(status);
                }
                if(!(refundSearchDTO.getAmountMore() == null)){
                    Predicate amountStart = criteriaBuilder.greaterThanOrEqualTo(root.get("refundAmount"), refundSearchDTO.getAmountMore());
                    predicates.add(amountStart);
                }
                if(!(refundSearchDTO.getAmountLess() == null)) {
                    Predicate amountStop = criteriaBuilder.lessThanOrEqualTo(root.get("refundAmount"), refundSearchDTO.getAmountLess());
                    predicates.add(amountStop);
                }
                if(!(refundSearchDTO.getCustomerName() == null)){
                    Predicate customerName = criteriaBuilder.like(criteriaBuilder.lower(root.get("book").get("payment").get("customer").get("fullName")),
                            "%"+refundSearchDTO.getCustomerName().toLowerCase()+"%");
                    predicates.add(customerName);
                }
                if(!(refundSearchDTO.getFacilityName() == null)){
                    Predicate facilityName = criteriaBuilder.like(criteriaBuilder.lower(root.get("book").get("payment").get("facility").get("name")),
                            "%"+refundSearchDTO.getFacilityName().toLowerCase()+"%");
                    predicates.add(facilityName);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
