package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.FacilitySearchDto;
import com.enigma.bookit.dto.request.UpdateFacilityRequest;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.Files;
import com.enigma.bookit.repository.FacilityRepository;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.service.FilesService;
import com.enigma.bookit.specification.FacilitySpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    FilesService filesService;
    @Autowired
    ModelMapper modelMapper;

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
    public Facility deleteFacility(String id) {
        facilityRepository.deleteById(id);
        return null;
    }

    @Override
    public Facility updateFacility(String id, UpdateFacilityRequest request, MultipartFile file) throws IOException {
          Files files = filesService.upload(file);
            String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(files.getId())
                .toUriString();
          request.setId(id);
          Facility facility = modelMapper.map(request, Facility.class);
            facility.setFiles(files);
            facility.getFiles().setUrl(downloadUrl);
            return facilityRepository.save(facility);
    }

    @Override
    public Page<Facility> getFacilityPerPage(Pageable pageable) {
        return facilityRepository.findAll(pageable);
    }



    @Override
    public Page<Facility> getFacilityPerPage(Pageable pageable, FacilitySearchDto facilitySearchDto) {
        Specification<Facility> facilitySpecification = FacilitySpecification.getSpesification(facilitySearchDto);
        return facilityRepository.findAll(facilitySpecification,pageable);
    }

}
