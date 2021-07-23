package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.ValidationConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.utils.DeleteResponse;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
        response.setData(customerService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CustomerDto>> getCustomerById(@PathVariable String id) {
        try {
            Response<CustomerDto> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.name());
            response.setData(customerService.getById(id));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex) {
            String error = ErrorMessageConstant.GET_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<CustomerDto>>> getAllCustomer() {
        try{
            Response<List<CustomerDto>> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.name());
            response.setData(customerService.getAll());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex) {
            String error = ErrorMessageConstant.GET_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CustomerDto>> updateCustomerDto(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        try {
            Response<CustomerDto> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK.name());
            response.setData(customerService.update(id, customerDto));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex) {
            String error = ErrorMessageConstant.GET_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @PutMapping("/update")
    public User changePassword(@RequestParam String id, @RequestBody User user) {
        return customerService.changePassword(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteCustomer(@PathVariable String id){
        try{
            DeleteResponse deleteResponse = new DeleteResponse();
            deleteResponse.setCode(HttpStatus.GONE.value());
            deleteResponse.setStatus(HttpStatus.GONE.name());
            customerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(deleteResponse);
        } catch (EmptyResultDataAccessException ex) {
            String error = ErrorMessageConstant.GET_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        Map<String, String> fieldErrorList = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(ValidationConstant.LABEL_TIMESTAMP, LocalDateTime.now());
            errors.put(ValidationConstant.LABEL_STATUS, HttpStatus.BAD_REQUEST.name());
            errors.put(ValidationConstant.LABEL_CODE, HttpStatus.BAD_REQUEST.value());

            String errorFieldList = ((FieldError) error).getField();
            String errorValueList = error.getDefaultMessage();
            fieldErrorList.put(errorFieldList, errorValueList);
            errors.put(ValidationConstant.LABEL_MESSAGE, fieldErrorList);
        });
        return errors;
    }

}
