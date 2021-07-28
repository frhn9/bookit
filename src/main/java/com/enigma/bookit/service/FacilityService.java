package com.enigma.bookit.service;

import com.enigma.bookit.dto.FacilitySearchDto;
import com.enigma.bookit.dto.request.UpdateFacilityRequest;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FacilityService {
    public Facility save (Facility facility);
    Facility getFacilityById(String id);
    List<Facility> getAllFacility();
    Facility deleteFacility(String id);
    Facility updateFacility(String id, UpdateFacilityRequest request, MultipartFile file) throws IOException;
    Page<Facility> getFacilityPerPage(Pageable pageable);
    public Page<Facility> getFacilityPerPage(Pageable pageable, FacilitySearchDto facilitySearchDto);
}