package com.enigma.bookit.service;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;

import java.util.List;

public interface CustomerService {
    Customer registerUser(User user);
    Customer changePassword(User user);
    CustomerDto update(String userName, CustomerDto customerDto);
    CustomerDto getCustomer(String fullName);
    List<CustomerDto> getAll();
    void deleteById(String id);
    Boolean validateUserData(User user);
    CustomerDto convertToDto(Customer customer);
    Customer convertToEntity(CustomerDto customerDto);
    Customer convertUserToEntity(User user);
    void validateUpdateData(Customer customer, CustomerDto customerDto);
