package com.enigma.bookit.controller;


import com.enigma.bookit.entity.Category;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facility")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @PostMapping
    public ResponseEntity<Response<Facility>> addNewFacility(@RequestBody Facility facility){
        Response<Facility> response = new Response<>();
        String message = String.format("NEW FACILITY INSERTED");
        response.setMessage(message);
        response.setData(facilityService.save(facility));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<Response<Facility>> getFacilityById(@PathVariable String facilityId){
        Response<Facility> response = new Response<>();
        String message = String.format("Get Data");
        response.setMessage(message);
        response.setData(facilityService.getFacilityById(facilityId));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }
    @GetMapping("/search")
    public List<Facility> getFacilityByName(@RequestParam(name="name") String name){
        return facilityService.getFacilityByNameContainingIgnoreCase(name);
    }

    @GetMapping
    public List<Facility> getAllFacility(){
        return facilityService.getAllFacility();
    }


    @PutMapping("/{facilityId}")
    public void updateFacility(@PathVariable("facilityId") String facilityId, @RequestBody Facility facility){
        facilityService.save(facility);
    }

    @DeleteMapping("/{facilityId}")
    public void  deleteFacility(@PathVariable("facilityId") String facilityId){
        facilityService.deleteFacility(facilityId);
    }
}

