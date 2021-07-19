package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.Customer;
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
    public void save(CustomerDto customerDto) {
        validateData(customerDto);
    }

    @Override
    public CustomerDto getById(String id) {
        CustomerDto customerDto = convertToDto(customerRepository.findById(id).get());
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
    public Customer validateData(CustomerDto customerDto) {
        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(customerDto);
        if (violations.size() == 0){
            Customer customer = convertToEntity(customerDto);
            return customerRepository.save(customer);
        }
        return null;
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

}
