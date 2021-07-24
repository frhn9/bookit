package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Response<UserDto>> registerCustomer(@Valid @RequestBody User user) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(SuccessMessageConstant.SUCCESS_CREATED_USER);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CustomerDto>> getCustomerById(@PathVariable String id) {
        Response<CustomerDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<CustomerDto>>> getAllCustomer() {
        Response<List<CustomerDto>> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.getAll());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CustomerDto>> updateCustomerDto(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        Response<CustomerDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.update(id, customerDto));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping()
    public ResponseEntity<Response<UserDto>> changePassword(@RequestParam String id, @RequestBody String password) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.changePassword(id, password));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable String id) {
        Response response = new Response();
        response.setCode(HttpStatus.GONE.value());
        response.setStatus(HttpStatus.GONE.name());
        response.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.getById(id));
        customerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(response);
    }


}
