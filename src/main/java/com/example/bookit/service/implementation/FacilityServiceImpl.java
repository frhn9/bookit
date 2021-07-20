package com.example.bookit.service.implementation;

import com.example.bookit.dto.FacilityDto;
import com.example.bookit.entity.Facility;
import com.example.bookit.repository.FacilityRepository;
import com.example.bookit.service.FacilityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    FacilityRepository facilityRepository;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void addFacility(FacilityDto facilityDto) {
        validateData(facilityDto);
    }

    @Override
    public FacilityDto getFacilityById(String id) {
        Facility facility = facilityRepository.findById(id).get();
        FacilityDto facilityDTO = modelMapper.map(facility, FacilityDto.class);
        return facilityDTO;
    }

    @Override
    public List<FacilityDto> getAllFacility() {
        List<Facility> facilityList = facilityRepository.findAll();
        List<FacilityDto> facilityDtoList =facilityList.stream().map(this::convertToDto).collect(Collectors.toList());
        return facilityDtoList;
    }

    @Override
    public List<Facility> getAllForOwner() {
        return facilityRepository.findAll();
    }

    @Override
    public void deleteFacility(String id) {
        facilityRepository.deleteById(id);
    }

    @Override
    public void updateFacility(String id, Facility facility) {
        if(getFacilityById(id) != null){
            facility.setId(id);
            facilityRepository.save(facility);
        }
    }

    @Override
    public Page<Facility> getFacilityPerPage(Pageable pageable) {
        return facilityRepository.findAll(pageable);
    }



    @Override
    public Facility validateData(FacilityDto facilityDto){
        Set<ConstraintViolation<FacilityDto>> violations = validator.validate(facilityDto);
        if(violations.size()  == 0){
            Facility facility =convertToEntity(facilityDto);
            return facilityRepository.save(facility);
        }
        return null;
    }

    @Override
    public FacilityDto convertToDto(Facility facility) {
        FacilityDto facilityDTO = modelMapper.map(facility, FacilityDto.class);
        return facilityDTO;
    }

    @Override
    public Facility convertToEntity(FacilityDto facilityDto) {
        Facility facility = modelMapper.map(facilityDto, Facility.class);
        return facility;
    }
}
