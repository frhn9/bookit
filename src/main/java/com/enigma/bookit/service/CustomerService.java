package com.enigma.bookit.service;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    UserDto registerUser(User user);

    UserDto changePassword(String id, UserPasswordDto userPassword);

    CustomerDto update(String id, CustomerDto customerDto);

    CustomerDto getById(String id);

    List<CustomerDto> getAll();

    Page<CustomerDto> getCustomerPerPage(Pageable pageable, UserSearchDto userSearchDto);

    Boolean deleteById(String id);

    void validateUpdateData(Customer customer, CustomerDto customerDto);

}
