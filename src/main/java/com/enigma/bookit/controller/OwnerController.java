package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.*;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.service.OwnerService;
import com.enigma.bookit.utils.DeleteResponse;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

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
        response.setMessage(SuccessMessageConstant.CREATED_USER_SUCCESSFUL);
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
    public ResponseEntity<Response<UserDto>> changePassword(@RequestParam String id, @RequestBody UserPasswordDto userPassword) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(ownerService.changePassword(id, userPassword));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteOwner(@PathVariable String id) {
        DeleteResponse deleteResponse = new DeleteResponse();
        ownerService.deleteById(id);

        deleteResponse.setCode(HttpStatus.GONE.value());
        deleteResponse.setStatus(HttpStatus.GONE.name());
        deleteResponse.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
        deleteResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(deleteResponse);
    }

    @GetMapping("/search")
    public PageResponseWrapper<OwnerDto> searchOwnerPerPage(@RequestBody UserSearchDto userSearchDto,
                                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                  @RequestParam(name = "sort", defaultValue = "fullName") String sort,
                                                                  @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        Sort sortBy = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<OwnerDto> ownerDtoPage = ownerService.getCustomerPerPage(pageable, userSearchDto);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        return new PageResponseWrapper<>(code, status, message, ownerDtoPage);
    }

}
