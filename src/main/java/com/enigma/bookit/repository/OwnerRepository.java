package com.enigma.bookit.repository;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
    Page<Owner> findAll(Specification<Owner> customerSpecification, Pageable pageable);
}
