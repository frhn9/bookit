package com.enigma.bookit.service;

import com.enigma.bookit.dto.*;
import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnerService {

    UserDto registerUser(User user);

    UserDto changePassword(String id, UserPasswordDto userPasswordDto);

    OwnerDto update(String id, OwnerDto ownerDto);

    OwnerDto getById(String id);

    List<OwnerDto> getAll();

    Page<OwnerDto> getCustomerPerPage(Pageable pageable, UserSearchDto userSearchDto);

    Boolean deleteById(String id);

    void validateUpdateData(Owner owner, OwnerDto ownerDto);
}
