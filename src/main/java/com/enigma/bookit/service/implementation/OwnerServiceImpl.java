package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.repository.OwnerRepository;
import com.enigma.bookit.service.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    private ModelMapper modelMapper;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Override
    public void save(OwnerDto ownerDto) {
        validateData(ownerDto);
    }

    @Override
    public OwnerDto getById(String id) {
        OwnerDto ownerDto = convertToDto(ownerRepository.findById(id).get());
        return ownerDto;
    }

    @Override
    public List<OwnerDto> getAll() {
        List<Owner> ownerList = ownerRepository.findAll();
        List<OwnerDto> ownerDtoList = ownerList.stream().map(this::convertToDto).collect(Collectors.toList());
        return ownerDtoList;
    }

    @Override
    public void deleteById(String id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner validateData(OwnerDto ownerDto) {
        Set<ConstraintViolation<OwnerDto>> violations = validator.validate(ownerDto);
        if (violations.size() == 0){
            Owner owner = convertToEntity(ownerDto);
            return ownerRepository.save(owner);
        }
        return null;
    }

    @Override
    public OwnerDto convertToDto(Owner owner) {
        OwnerDto ownerDto = modelMapper.map(owner, OwnerDto.class);
        return ownerDto;
    }

    @Override
    public Owner convertToEntity(OwnerDto ownerDto) {
        Owner owner = modelMapper.map(ownerDto, Owner.class);
        return owner;
    }

}
