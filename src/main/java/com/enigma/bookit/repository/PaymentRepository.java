package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.specification.PaymentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Page<Payment> findAll (Specification<Payment> paymentSpecification, Pageable pageable);

    List<Payment> findAll(Specification<Payment> paymentSpecification);
}
