package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.exception.GlobalControllerExceptionHandler;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.utils.DeleteResponse;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PutMapping
    public ResponseEntity<Response<UserDto>> changePassword(@RequestParam String id, @RequestBody UserPasswordDto userPassword) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.changePassword(id, userPassword));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCustomer(@PathVariable String id) {
        DeleteResponse deleteResponse = new DeleteResponse();
        customerService.deleteById(id);

        deleteResponse.setCode(HttpStatus.GONE.value());
        deleteResponse.setStatus(HttpStatus.GONE.name());
        deleteResponse.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
        deleteResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(deleteResponse);
    }

    @GetMapping("/search")
    public PageResponseWrapper<CustomerDto> searchCustomerPerPage(@RequestBody UserSearchDto userSearchDto,
                                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                  @RequestParam(name = "sort", defaultValue = "fullName") String sort,
                                                                  @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        Sort sortBy = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<CustomerDto> customerDtoPage = customerService.getCustomerPerPage(pageable, userSearchDto);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        return new PageResponseWrapper<>(code, status, message, customerDtoPage);
    }
}
