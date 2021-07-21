package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {
    public List<Facility> getFacilityByNameContainingIgnoreCase (String name);
}
