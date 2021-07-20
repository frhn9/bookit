package com.example.bookit.controller;


import com.example.bookit.dto.FacilityDto;
import com.example.bookit.entity.Facility;
import com.example.bookit.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facility")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @PostMapping
    public void addNewFacility(@RequestBody FacilityDto facilityDto){
        facilityService.save(facilityDto);
    }

    @GetMapping("/{facilityId}")
    public FacilityDto getFacilityById(@PathVariable String facilityId){
        return facilityService.getFacilityById(facilityId);

    }

    @GetMapping
    public List<FacilityDto> getAllFacility(){
        return facilityService.getAllFacility();
    }

    @GetMapping("/owner")
    public List<Facility> getAllFacilityForOwner(){
        return facilityService.getAllForOwner();
    }

    @PutMapping("/{facilityId}")
    public void updateFacility(@PathVariable("facilityId") String facilityId, @RequestBody FacilityDto facilityDto){
        facilityService.save(facilityDto);
    }

    @DeleteMapping("/{facilityId}")
    public void  deleteFacility(@PathVariable("facilityId") String facilityId){
        facilityService.deleteFacility(facilityId);
    }
}

