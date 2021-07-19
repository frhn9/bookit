package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.Customer;
import com.enigma.bookit.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public void createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.save(customerDto);
    }

    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable String customerId){
        return customerService.getById(customerId);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomer(){
        return customerService.getAll();
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable String customerId){
        customerService.deleteById(customerId);
    }

}
