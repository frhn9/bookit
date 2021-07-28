package com.enigma.bookit.controller;

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
import com.enigma.bookit.security.services.CustomerDetailsImpl;
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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl customerService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/customers/signin")
    public ResponseEntity<Response<JwtResponse>> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        List<String> roles = customerDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Response<JwtResponse> response = new Response<>();

        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(SuccessMessageConstant.LOGIN_USER_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(new JwtResponse(jwt, customerDetails.getId(), customerDetails.getUsername(),
                customerDetails.getFullName(), customerDetails.getAddress(), customerDetails.getContact(),
                customerDetails.getGender(), customerDetails.getEmail(), customerDetails.getJob(),
                customerDetails.getDateOfBirth(), roles));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

//    @PostMapping("/owners/signin")
//    public ResponseEntity<Response<OwnerJwtResponse>> authenticateOwner(@Valid @RequestBody LoginRequest loginRequest){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = ownerJwtUtils.generateJwtToken(authentication);
//
//        OwnerDetailsImpl ownerDetails = (OwnerDetailsImpl) authentication.getPrincipal();
//        List<String> roles = ownerDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        Response<OwnerJwtResponse> response = new Response<>();
//
//        response.setCode(HttpStatus.OK.value());
//        response.setStatus(HttpStatus.OK.name());
//        response.setMessage(SuccessMessageConstant.LOGIN_USER_SUCCESSFUL);
//        response.setTimestamp(LocalDateTime.now());
//        response.setData(new OwnerJwtResponse(jwt, ownerDetails.getId(), ownerDetails.getUsername(),
//                ownerDetails.getFullName(), ownerDetails.getAddress(), ownerDetails.getContact(),
//                ownerDetails.getGender(), ownerDetails.getEmail(), ownerDetails.getDateOfBirth(), roles));
//
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
//    }

    @PostMapping("/customers/signup")
    public ResponseEntity<Response<UserDto>> registerCustomer(@Valid @RequestBody SignupRequest signupRequest) {
        if (customerService.userNameExist(signupRequest.getUserName())) {
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
        customerService.registerUser(user);

        Response<UserDto> response = new Response<>();

        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(SuccessMessageConstant.CREATED_USER_SUCCESSFUL);
        response.setTimestamp(LocalDateTime.now());
        response.setData(customerService.registerUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
    }

//    @PostMapping("/owners/signup")
//    public ResponseEntity<Response<UserDto>> registerOwner(@Valid @RequestBody SignupRequest signupRequest){
//        if (ownerService.userNameExist(signupRequest.getUserName())) {
//            throw new KeyAlreadyExistsException();
//        }
//
//        Owner owner = new Owner(signupRequest.getFullName(),
//                signupRequest.getUserName(),
//                passwordEncoder.encode(signupRequest.getPassword()),
//                signupRequest.getEmail());
//
//        Set<String> strRoles = signupRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_OWNER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(NoSuchElementException::new);
//                        roles.add(adminRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_OWNER)
//                                .orElseThrow(NoSuchElementException::new);
//                        roles.add(userRole);
//                }
//            });
//        }
//        owner.setRoles(roles);
//        customerService.registerUser(owner);
//
//        Response<UserDto> response = new Response<>();
//
//        response.setCode(HttpStatus.CREATED.value());
//        response.setStatus(HttpStatus.CREATED.name());
//        response.setMessage(SuccessMessageConstant.CREATED_USER_SUCCESSFUL);
//        response.setTimestamp(LocalDateTime.now());
//        response.setData(ownerService.registerUser(owner));
//        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
//    }

}
