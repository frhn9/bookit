package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Customer;
import com.enigma.bookit.repository.CustomerRepository;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.result.Output;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Autowired
    MockMvc mockMvc;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private Customer customer;

    @BeforeEach
    void setup(){
        customer = new Customer();

        customer.setId("cust01");
        customer.setFullName("fadiel");
        customer.setAddress("Perum PU Bandung");
        customer.setContact("0821123456");
        customer.setEmail("just_fadhyl@hotmail.co.id");
        customer.setGender("Cwk");
        customer.setUserName("username");
        customer.setPassword("password");
        customer.setJob("Pengangguran");
    }

    @Test
    void save_shouldBeSuccess() {
//        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
//
//        customerService.save(customer);
//        List<Customer> customers = new ArrayList<>();
//        customers.add(customer);
//
//        when(customerRepository.findAll()).thenReturn(customers);
//        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void validation_shouldBeSuccess(){
        Customer output = new Customer();
        output.setFullName("fadiel");
        output.setJob("Scum master");

        Set<ConstraintViolation<Customer>> violations = validator.validate(output);
        assertEquals(3, violations.size());
    }

    void getById() {
    }

    void getAll() {
    }

    void deleteById() {
    }
}