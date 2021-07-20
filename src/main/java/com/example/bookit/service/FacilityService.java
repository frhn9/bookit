package com.example.bookit.service;

import com.example.bookit.dto.FacilityDto;
import com.example.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacilityService {
    public void addFacility (FacilityDto facilityDto);
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
