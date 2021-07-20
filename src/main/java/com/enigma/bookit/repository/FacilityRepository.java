package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, String> {
}
