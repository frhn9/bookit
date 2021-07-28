package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository <Refund, String> {
    Page<Refund> findAll(Specification<Refund>refundSpecification, Pageable pageable);

}
