package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.ERole;
import com.enigma.bookit.entity.user.Role;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.RoleRepository;
import com.enigma.bookit.security.jwt.JwtUtils;
import com.enigma.bookit.security.payload.request.LoginRequest;
import com.enigma.bookit.security.payload.request.SignupRequest;
import com.enigma.bookit.security.payload.response.JwtResponse;
import com.enigma.bookit.security.services.UserDetailsImpl;
import com.enigma.bookit.service.implementation.UserServiceImpl;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiUrlConstant.AUTH)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(ApiUrlConstant.AUTH_SIGN_IN)
    public ResponseEntity<Response<JwtResponse>> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Response<JwtResponse> response = new Response<>();

        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.LOGIN_USER_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getFullName(), userDetails.getAddress(), userDetails.getContact(),
                userDetails.getGender(), userDetails.getEmail(), userDetails.getJob(),
                userDetails.getDateOfBirth(), roles));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PostMapping(ApiUrlConstant.AUTH_SIGN_UP_CUSTOMER)
    public ResponseEntity<Response<UserDto>> registerCustomer(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.userNameExist(signupRequest.getUserName())) {
            throw new KeyAlreadyExistsException();
        }

        User user = new User(signupRequest.getFullName(),
                signupRequest.getUserName(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(NoSuchElementException::new);
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(NoSuchElementException::new);
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userService.registerUser(user);

        Response<UserDto> response = new Response<>();

        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(SuccessMessageConstant.CREATED_USER_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PostMapping(ApiUrlConstant.AUTH_SIGN_UP_OWNER)
    public ResponseEntity<Response<UserDto>> registerOwner(@Valid @RequestBody SignupRequest signupRequest){
        if (userService.userNameExist(signupRequest.getUserName())) {
            throw new KeyAlreadyExistsException();
        }

        User user = new User(signupRequest.getFullName(),
                signupRequest.getUserName(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_OWNER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(NoSuchElementException::new);
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_OWNER)
                                .orElseThrow(NoSuchElementException::new);
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userService.registerUser(user);

        Response<UserDto> response = new Response<>();

        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(SuccessMessageConstant.CREATED_USER_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(userService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

}
