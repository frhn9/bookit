package com.enigma.bookit.service;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.entity.Customer;
import com.enigma.bookit.entity.Owner;

import java.util.List;

public interface OwnerService {
    void save(OwnerDto ownerDto);
    OwnerDto getById(String id);
    List<OwnerDto> getAll();
    void deleteById(String id);
    Owner validateData(OwnerDto ownerDto);
    OwnerDto convertToDto(Owner owner);
    Owner convertToEntity(OwnerDto ownerDto);
//    void save(CustomerDto customerDto);
//    CustomerDto getById(String id);
//    List<CustomerDto> getAll();
//    void deleteById(String id);
//    Customer validateData(CustomerDto customerDto);
//    CustomerDto convertToDto(Customer customer);
//    Customer convertToEntity(CustomerDto customerDto);
}
