package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void register_shouldSave() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("just_fadhyl@hotmail.co.id");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDeletedAt(LocalDateTime.now());

        customerService.registerUser(user);
        customerRepository.save(customerService.convertUserToEntity(user));
        List<User> users = new ArrayList<>();
        users.add(user);

        Customer customer = customerService.convertUserToEntity(user);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void shouldUpdate() {
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("fadiel@gmail.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setDeletedAt(LocalDateTime.now());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId("usersuccess");
        customerDto.setFullName("dinny");
        customerDto.setEmail("dinny@gmail.com");
        customerDto.setAddress("Bandung Yahud");
        customerDto.setContact("082112345");
        customerDto.setGender("cwk");
        customerDto.setJob("nganggur bos");

        customerRepository.save(customer);
        when(customerRepository.findById("usersuccess")).thenReturn(Optional.of(customer));
        customerService.update("usersuccess", customerDto);

        when(customerRepository.findById("usersuccess")).thenReturn(Optional.of(customer));
        assertEquals("dinny", customerRepository.findById("usersuccess").get().getFullName());
        assertEquals("Bandung Yahud", customerRepository.findById("usersuccess").get().getAddress());
        assertEquals("nganggur bos", customerRepository.findById("usersuccess").get().getJob());
    }

    @Test
    void shouldGetById() {
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("fadiel@gmail.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setDeletedAt(LocalDateTime.now());

        CustomerDto customerDto = new CustomerDto();

        customerRepository.save(customer);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        customerDto.setId(customerService.getById("usersuccess").getId());
        customerDto.setFullName(customerService.getById("usersuccess").getFullName());
        customerDto.setEmail(customerService.getById("usersuccess").getEmail());

        assertEquals(customer.getId(), customerDto.getId());
        assertEquals(customer.getFullName(), customerDto.getFullName());
        assertEquals(customer.getEmail(), customerDto.getEmail());
    }

    @Test
    void shouldGetAll() {
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("fadiel@gmail.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setEmail(customer.getEmail());

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDto> customersDto = customerService.getAll();

        assertEquals(1, customersDto.size());
    }

    @Test
    void shouldDeleteById() {
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("fadiel@gmail.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);
        customerService.deleteById("usersuccess");

        assertEquals(0, customerRepository.findAll().size());
    }

}