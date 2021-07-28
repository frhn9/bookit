package com.enigma.bookit.specification;

import com.enigma.bookit.dto.BookSearchDto;
import com.enigma.bookit.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

public class BookSpecification {
    public static Specification<Book> getSpesification(BookSearchDto bookSearchDto){
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(bookSearchDto.getActiveUntil() != null){
                    Predicate bookActiveUntil = criteriaBuilder.lessThanOrEqualTo(root.get("activeUntil"), bookSearchDto.getActiveUntil());
                    predicates.add(bookActiveUntil);
                }
                if(bookSearchDto.getActiveFrom() != null){
                    Predicate bookActiveFrom = criteriaBuilder.greaterThanOrEqualTo(root.get("activeFrom"), bookSearchDto.getActiveFrom());
                    predicates.add(bookActiveFrom);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}

