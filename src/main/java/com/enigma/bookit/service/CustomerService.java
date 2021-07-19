package com.enigma.bookit.service;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.Customer;

import java.util.List;

public interface CustomerService {
    void save(CustomerDto customerDto);
    CustomerDto getById(String id);
    List<CustomerDto> getAll();
    void deleteById(String id);
    Customer validateData(CustomerDto customerDto);
    CustomerDto convertToDto(Customer customer);
    Customer convertToEntity(CustomerDto customerDto);
}
