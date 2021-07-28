package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.service.UserService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

//TAMBAHIN IMPLICIT PARAMNYA

@RestController
@RequestMapping(ApiUrlConstant.USER)
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Response<UserDto>> registerUser(@Valid @RequestBody User user) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(SuccessMessageConstant.CREATED_USER_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Response<UserDto>> getUserById(@PathVariable String id) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<List<UserDto>>> getAllUser() {
        Response<List<UserDto>> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.getAll());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')") //Tambahin ROLE_OWNER
    public ResponseEntity<Response<UserDto>> updateUserDto(@PathVariable String id, @RequestBody UserDto userDto) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.update(id, userDto));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')") //Tambahin ROLE_OWNER
    public ResponseEntity<Response<UserDto>> changePassword(@RequestParam String id, @RequestBody UserPasswordDto userPassword) {
        Response<UserDto> response = new Response<>();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.changePassword(id, userPassword));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')") //Tambahin ROLE_ADMIN, ROLE_OWNER
    public ResponseEntity<DeleteResponse> deleteUser(@PathVariable String id) {
        DeleteResponse deleteResponse = new DeleteResponse();
        userService.deleteById(id);

        deleteResponse.setCode(HttpStatus.GONE.value());
        deleteResponse.setStatus(HttpStatus.GONE.name());
        deleteResponse.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
        deleteResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.GONE).contentType(MediaType.APPLICATION_JSON).body(deleteResponse);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PageResponseWrapper<UserDto> searchUserPerPage(@RequestBody UserSearchDto userSearchDto,
                                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                          @RequestParam(name = "sort", defaultValue = "fullName") String sort,
                                                          @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        Sort sortBy = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<UserDto> userDtoPage = userService.getCustomerPerPage(pageable, userSearchDto);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        return new PageResponseWrapper<>(code, status, message, userDtoPage);
    }
}
