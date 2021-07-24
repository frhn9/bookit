package com.enigma.bookit.service;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.entity.user.User;

import java.util.List;

public interface OwnerService {

    UserDto registerUser(User user);

    UserDto changePassword(String id, String password);

    OwnerDto update(String id, OwnerDto ownerDto);

    OwnerDto getById(String id);

    List<OwnerDto> getAll();

    void deleteById(String id);

    void validateUpdateData(Owner owner, OwnerDto ownerDto);
}
