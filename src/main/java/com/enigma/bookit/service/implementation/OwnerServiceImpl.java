package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.OwnerRepository;
import com.enigma.bookit.service.OwnerService;
import com.enigma.bookit.service.converter.UserConverter;
import com.enigma.bookit.specification.OwnerSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService, UserConverter {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto registerUser(User user) {
        Owner owner = ownerRepository.save(convertUserToEntity(user));

        user = convertEntityToUser(owner);
        return convertUserToUserDto(user);
    }

    @Override
    public UserDto changePassword(String id, UserPasswordDto userPassword) {
        Owner owner = ownerRepository.findById(id).get();
        owner.setPassword(userPassword.getPassword());

        owner = ownerRepository.save(owner);
        User user = convertEntityToUser(owner);
        return convertUserToUserDto(user);
    }

    @Override
    public OwnerDto update(String id, OwnerDto ownerDto) {
        Owner owner = ownerRepository.findById(id).get();
        validateUpdateData(owner, ownerDto);

        owner = ownerRepository.save(owner);
        return convertEntityToDto(owner);
    }

    @Override
    public OwnerDto getById(String id) {
        return convertEntityToDto(ownerRepository.findById(id).get());
    }

    @Override
    public List<OwnerDto> getAll() {
        List<Owner> ownerList = ownerRepository.findAll();
        return ownerList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Page<OwnerDto> getCustomerPerPage(Pageable pageable, UserSearchDto userSearchDto) {
        Specification<Owner> ownerSpecification = OwnerSpecification.getSpecification(userSearchDto);
        Page<Owner> getOwnerData = ownerRepository.findAll(ownerSpecification, pageable);
        return getOwnerData.map(this::convertEntityToDto);
    }

    @Override
    public Boolean deleteById(String id) {
        Boolean isFound = ownerRepository.existsById(id);
        ownerRepository.deleteById(id);
        return isFound;
    }

    @Override
    public void validateUpdateData(Owner owner, OwnerDto ownerDto) {
        if(ownerDto.getFullName() != null) owner.setFullName(ownerDto.getFullName());
        if (ownerDto.getAddress() != null) owner.setAddress(ownerDto.getAddress());
        if (ownerDto.getContact() != null) owner.setContact(ownerDto.getContact());
        if (ownerDto.getEmail() != null) owner.setContact(ownerDto.getEmail());
        if (ownerDto.getGender() != null) owner.setGender(ownerDto.getGender());
    }

    @Override
    public OwnerDto convertEntityToDto(Object entity) {
        return modelMapper.map(entity, OwnerDto.class);
    }

    @Override
    public Owner convertUserToEntity(Object user) {
        return modelMapper.map(user, Owner.class);
    }

    @Override
    public User convertEntityToUser(Object entity) {
        return modelMapper.map(entity, User.class);
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Boolean userNameExist(String userName){
        return ownerRepository.existsByUserName(userName);
    }

}
