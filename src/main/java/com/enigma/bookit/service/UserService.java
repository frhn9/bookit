package com.enigma.bookit.service;

import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto registerUser(User user);
    UserDto changePassword(String id, UserPasswordDto userPassword);
    UserDto update(String id, UserDto userDto);
    UserDto getById(String id);
    List<UserDto> getAll();
    Page<UserDto> getCustomerPerPage(Pageable pageable, UserSearchDto userSearchDto);
    Boolean deleteById(String id);
    void validateUpdateData(User user, UserDto userDto);
    Boolean userNameExist(String userName);
}
