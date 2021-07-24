package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.OwnerRepository;
import com.enigma.bookit.service.OwnerService;
import com.enigma.bookit.service.converter.UserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        Owner owner = convertUserToEntity(user);
        ownerRepository.save(owner);
        return convertUserToUserDto(user);
    }

    @Override
    public UserDto changePassword(String id, String password) {
        Owner owner = ownerRepository.findById(id).get();
        owner.setPassword(password);
        User user = convertEntityToUser(owner);
        return convertUserToUserDto(user);
    }

    @Override
    public OwnerDto update(String id, OwnerDto ownerDto) {
        Owner owner = ownerRepository.findById(id).get();
        validateUpdateData(owner, ownerDto);
        ownerRepository.save(owner);
        return ownerDto;
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
    public void deleteById(String id) {
        ownerRepository.deleteById(id);
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

}
