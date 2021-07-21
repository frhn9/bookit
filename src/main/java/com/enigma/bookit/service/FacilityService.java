package com.enigma.bookit.service;

import com.enigma.bookit.dto.FacilityDto;
import com.enigma.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacilityService {
    void save (Facility facility);
    FacilityDto getFacilityById(String id);
    List<FacilityDto> getAllFacility();
    List<Facility> getAllForOwner();
    void deleteFacility(String id);
    void updateFacility(String id, Facility facility);
    Page<Facility> getFacilityPerPage(Pageable pageable);
    Facility validateData(FacilityDto facilityDto);
    FacilityDto convertToDto(Facility facility);
    Facility convertToEntity(FacilityDto facilityDto);

}
