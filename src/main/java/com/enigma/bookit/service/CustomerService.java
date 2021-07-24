package com.enigma.bookit.service;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;

import java.util.List;

public interface CustomerService {
    UserDto registerUser(User user);

    UserDto changePassword(String id, String password);

    CustomerDto update(String id, CustomerDto customerDto);

    CustomerDto getById(String id);

    List<CustomerDto> getAll();

    void deleteById(String id);

    void validateUpdateData(Customer customer, CustomerDto customerDto);

}
