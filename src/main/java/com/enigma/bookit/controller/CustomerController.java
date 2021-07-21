package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public void registerCustomer(@RequestBody User user){
        customerService.registerUser(user);
    }

    @GetMapping("/{userName}")
    public CustomerDto getCustomerById(@PathVariable String userName){
        return customerService.getCustomer(userName);
    }

    @PutMapping("/{userName}")
    public CustomerDto updateCustomerDto(@PathVariable String userName, @RequestBody CustomerDto customerDto){
        return customerService.update(userName, customerDto);
    }

    @PutMapping("/update")
    public User changePassword(@RequestParam String userName, @RequestBody User user){
        return customerService.changePassword(user);
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
