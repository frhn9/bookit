package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.constant.ResponseLabelConstant;
import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.service.OwnerService;
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
@RequestMapping(ApiUrlConstant.OWNER)
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Response<UserDto>> registerOwner(@Valid @RequestBody User user){
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(SuccessMessageConstant.SUCCESS_CREATED_USER);
        response.setData(ownerService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<OwnerDto>> getOwnerById(@PathVariable String id){
        try {
            Response<OwnerDto> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
            response.setData(ownerService.getById(id));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex) {
            String error = ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<OwnerDto>>> getAllOwner(){
        try{
            Response<List<OwnerDto>> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
            response.setData(ownerService.getAll());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex) {
            String error = ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<OwnerDto>> updateOwnerDto(@PathVariable String id, @RequestBody OwnerDto ownerDto) {
        try {
            Response<OwnerDto> response = new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL);
            response.setData(ownerService.update(id, ownerDto));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex) {
            String error = ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @PutMapping()
    public ResponseEntity<Response<UserDto>> changePassword(@RequestParam String id, @RequestBody String password){
        try{
            Response<UserDto> response =  new Response<>();
            response.setCode(HttpStatus.OK.value());
            response.setStatus(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL);
            response.setData(ownerService.changePassword(id, password));
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (NoSuchElementException ex){
            String error = ErrorMessageConstant.CHANGE_PASSWORD_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteOwner(@PathVariable String id){
        try{
            DeleteResponse deleteResponse = new DeleteResponse();
            deleteResponse.setCode(HttpStatus.GONE.value());
            deleteResponse.setStatus(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
            ownerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(deleteResponse);
        } catch (EmptyResultDataAccessException ex) {
            String error = ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        Map<String, String> fieldErrorList = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(ResponseLabelConstant.LABEL_TIMESTAMP, LocalDateTime.now());
            errors.put(ResponseLabelConstant.LABEL_STATUS, ErrorMessageConstant.CREATED_USER_FAILED);
            errors.put(ResponseLabelConstant.LABEL_CODE, HttpStatus.BAD_REQUEST.value());

            String errorFieldList = ((FieldError) error).getField();
            String errorValueList = error.getDefaultMessage();
            fieldErrorList.put(errorFieldList, errorValueList);
            errors.put(ResponseLabelConstant.LABEL_MESSAGE, fieldErrorList);
        });
        return errors;
    }
}
