package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import com.enigma.bookit.service.converter.UserConverter;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.specification.CustomerSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService, UserConverter {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto registerUser(User user) {
        Customer customer = customerRepository.save(convertUserToEntity(user));
        user = convertEntityToUser(customer);

        return convertUserToUserDto(user);
    }

    @Override
    public UserDto changePassword(String id, UserPasswordDto userPassword) {
        Customer customer = customerRepository.findById(id).get();
        customer.setPassword(userPassword.getPassword());

        customer = customerRepository.save(customer);
        User user = convertEntityToUser(customer);
        return convertUserToUserDto(user);
    }

    @Override
    public CustomerDto update(String id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).get();
        validateUpdateData(customer, customerDto);

        customer = customerRepository.save(customer);
        return convertEntityToDto(customer);
    }

    @Override
    public CustomerDto getById(String id) {
        return convertEntityToDto(customerRepository.findById(id).get());
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Page<CustomerDto> getCustomerPerPage(Pageable pageable, UserSearchDto userSearchDto) {
        Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(userSearchDto);
        Page<Customer> getCustomerData = customerRepository.findAll(customerSpecification, pageable);
        return getCustomerData.map(this::convertEntityToDto);
    }

    @Override
    public Boolean deleteById(String id) {
        Boolean isFound = customerRepository.existsById(id);
        customerRepository.deleteById(id);
        return isFound;
    }

    @Override
    public void validateUpdateData(Customer customer, CustomerDto customerDto) {
        if(customerDto.getFullName() != null) customer.setFullName(customerDto.getFullName());
        if (customerDto.getAddress() != null) customer.setAddress(customerDto.getAddress());
        if (customerDto.getContact() != null) customer.setContact(customerDto.getContact());
        if (customerDto.getEmail() != null) customer.setContact(customerDto.getEmail());
        if (customerDto.getGender() != null) customer.setGender(customerDto.getGender());
        if (customerDto.getJob() != null) customer.setJob(customerDto.getJob());
    }

    @Override
    public CustomerDto convertEntityToDto(Object entity) {
        return modelMapper.map(entity, CustomerDto.class);
    }

    @Override
    public Customer convertUserToEntity(Object user) {
        return modelMapper.map(user, Customer.class);
    }

    @Override
    public User convertEntityToUser(Object entity) {
        return modelMapper.map(entity, User.class);
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
