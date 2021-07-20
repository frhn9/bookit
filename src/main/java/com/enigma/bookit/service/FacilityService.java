package com.enigma.bookit.service;

import com.enigma.bookit.dto.FacilityDto;
import com.enigma.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacilityService {
    public void save (FacilityDto facilityDto);
    public FacilityDto getFacilityById(String id);
    public List<FacilityDto> getAllFacility();
    public List<Facility> getAllForOwner();
    public void deleteFacility(String id);
    void updateFacility(String id, Facility facility);
    public Page<Facility> getFacilityPerPage(Pageable pageable);
    public Facility validateData(FacilityDto facilityDto);
    public FacilityDto convertToDto(Facility facility);
    public Facility convertToEntity(FacilityDto facilityDto);

}
