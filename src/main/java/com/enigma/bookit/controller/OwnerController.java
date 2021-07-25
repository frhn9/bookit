package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(ApiUrlConstant.OWNER)
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Response<UserDto>> registerOwner(@Valid @RequestBody User user) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(SuccessMessageConstant.SUCCESS_CREATED_USER);
        response.setTimestamp(LocalDateTime.now());
        response.setData(ownerService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<OwnerDto>> getOwnerById(@PathVariable String id) {
        Response<OwnerDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(ownerService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<OwnerDto>>> getAllOwner() {
        Response<List<OwnerDto>> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(ownerService.getAll());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<OwnerDto>> updateOwnerDto(@PathVariable String id, @RequestBody OwnerDto ownerDto) {
        Response<OwnerDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(ownerService.update(id, ownerDto));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping()
    public ResponseEntity<Response<UserDto>> changePassword(@RequestParam String id, @RequestBody String password) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(ownerService.changePassword(id, password));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteOwner(@PathVariable String id) {
        Response response = new Response();
        response.setCode(HttpStatus.GONE.value());
        response.setStatus(HttpStatus.GONE.name());
        response.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        ownerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(response);
    }

}
