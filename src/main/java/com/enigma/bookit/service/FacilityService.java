package com.enigma.bookit.service;

import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacilityService {
    public Facility save (Facility facility);
    Facility getFacilityById(String id);
    List<Facility> getAllFacility();
    void deleteFacility(String id);
    void updateFacility(String id, Facility facility);
    Page<Facility> getFacilityPerPage(Pageable pageable);
    List<Facility> getFacilityByNameContainingIgnoreCase(String name);
    void validatePresent(String id);
}
