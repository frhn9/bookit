package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.FacilityRepository;
import com.enigma.bookit.service.FacilityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    FacilityRepository facilityRepository;

    @Override
    public Facility save(Facility facility) {
       return facilityRepository.save(facility);
    }

    @Override
    public Facility getFacilityById(String id) {
        return facilityRepository.findById(id).get();
    }

    @Override
    public List<Facility> getAllFacility() {
        return facilityRepository.findAll();
    }


    @Override
    public void deleteFacility(String id) {
        validatePresent(id);
        facilityRepository.deleteById(id);
    }

    @Override
    public void updateFacility(String id, Facility facility) {
            validatePresent(id);
            facility.setId(id);
            facilityRepository.save(facility);
        }

    @Override
    public Page<Facility> getFacilityPerPage(Pageable pageable) {
        return facilityRepository.findAll(pageable);
    }

    @Override
    public List<Facility> getFacilityByNameContainingIgnoreCase(String name) {
        return facilityRepository.getFacilityByNameContainingIgnoreCase(name);
    }

    @Override
    public void validatePresent(String id) {
            if(!facilityRepository.findById(id).isPresent()){
                String message = "id not found";
                throw new DataNotFoundException(message);
            }
        }
    }
