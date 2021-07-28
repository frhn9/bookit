package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.UserRepository;
import com.enigma.bookit.service.converter.UserConverter;
import com.enigma.bookit.service.UserService;
import com.enigma.bookit.specification.UserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserConverter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto registerUser(User user) {
        User registration = userRepository.save(user);

        return convertUserToDto(registration);
    }

    @Override
    public UserDto changePassword(String id, UserPasswordDto userPassword) {
        User user = userRepository.findById(id).get();
        user.setPassword(userPassword.getPassword());
        return convertUserToDto(userRepository.save(user));
    }

    @Override
    public UserDto update(String id, UserDto userDto) {
        User user = userRepository.findById(id).get();
        validateUpdateData(user, userDto);

        user = userRepository.save(user);
        return convertUserToDto(user);
    }

    @Override
    public UserDto getById(String id) {
        System.out.println(userRepository.findById(id).get());
        return convertUserToDto(userRepository.findById(id).get());
    }

    @Override
    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertUserToDto).collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> getCustomerPerPage(Pageable pageable, UserSearchDto userSearchDto) {
        Specification<User> userSpecification = UserSpecification.getSpecification(userSearchDto);
        Page<User> getUserData = userRepository.findAll(userSpecification, pageable);
        return getUserData.map(this::convertUserToDto);
    }

    @Override
    public Boolean deleteById(String id) {
        Boolean isFound = userRepository.existsById(id);
        userRepository.deleteById(id);
        return isFound;
    }

    @Override
    public void validateUpdateData(User user, UserDto userDto) {
        if(userDto.getFullName() != null) user.setFullName(userDto.getFullName());
        if (userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if (userDto.getContact() != null) user.setContact(userDto.getContact());
        if (userDto.getEmail() != null) user.setContact(userDto.getEmail());
        if (userDto.getGender() != null) user.setGender(userDto.getGender());
        if (userDto.getJob() != null) user.setJob(userDto.getJob());
    }

    @Override
    public UserDto convertUserToDto(Object entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public User convertDtoToUser(Object user) {
        return modelMapper.map(user, User.class);
    }

    @Override
    public Boolean userNameExist(String userName){
        return userRepository.existsByUserName(userName);
    }
}
