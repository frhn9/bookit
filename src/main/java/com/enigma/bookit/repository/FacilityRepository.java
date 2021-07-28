package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {
    Page<Facility> findAll(Specification<Facility> facilitySpecification, Pageable pageable);
}
