package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import com.enigma.bookit.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Override
    public Customer registerUser(User user) {
        Boolean validateCheck = validateUserData(user);
        if (validateCheck) {
            Customer customer = convertUserToEntity(user);
            return customerRepository.save(customer);
        }
        return null;
    }

    @Override
    public Customer changePassword(User user) {
        return null;
    }

    @Override // UPDATENYA ERROR, FIXIN BUAT BESOK
    public CustomerDto update(String userName, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(userName).get();
        validateUpdateData(customer, customerDto);
        customerRepository.save(customer);
        return customerDto;
    }

    @Override
    public CustomerDto getCustomer(String userName) {
        CustomerDto customerDto = convertToDto(customerRepository.findById(userName).get());
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = customerList.stream().map(this::convertToDto).collect(Collectors.toList());
        return customerDtoList;
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Boolean validateUserData(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        return violations.isEmpty();
    }

    @Override
    public void validateUpdateData(Customer customer, CustomerDto customerDto) {
        if(customerDto.getFullName() != null){
            customer.setFullName(customerDto.getFullName());
        } if (customerDto.getAddress() != null){
            customer.setAddress(customerDto.getAddress());
        } if (customerDto.getContact() != null){
            customer.setContact(customerDto.getContact());
        } if (customerDto.getEmail() != null){
            customer.setContact(customerDto.getEmail());
        } if (customerDto.getGender() != null){
            customer.setGender(customerDto.getGender());
        } if (customerDto.getJob() != null){
            customer.setJob(customerDto.getJob());
        }
    }

    @Override
    public CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }

    @Override
    public Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }

    @Override
    public Customer convertUserToEntity(User user) {
        Customer customer = modelMapper.map(user, Customer.class);
        return customer;
    }

}
