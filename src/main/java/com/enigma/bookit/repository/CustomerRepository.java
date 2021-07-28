package com.enigma.bookit.repository;

import com.enigma.bookit.entity.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> findAll(Specification<Customer> customerSpecification, Pageable pageable);

    Optional<Customer> findByUserName(String userName);

    Boolean existsByUserName(String userName);
}
