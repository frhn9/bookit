package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import com.enigma.bookit.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Mock
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Autowired
    MockMvc mockMvc;

    @Mock
    ModelMapper modelMapper;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private User userSuccess;
    private User userFailed;
    private CustomerDto customerDtoSuccess;
    private CustomerDto customerDtoFail;

    private Customer customerEntity = new Customer();
    private CustomerDto customerDTO = new CustomerDto();

    @BeforeEach
    void setup(){
        userSuccess = new User();
        userFailed = new User();

        userSuccess.setUserName("admin");
        userSuccess.setPassword("1234");
        userSuccess.setFullName("fadiel");
        userSuccess.setEmail("just_fadhyl@hotmail.co.id");

        userFailed.setEmail("asal@gmail.com");

        customerDtoSuccess = new CustomerDto();
        customerDtoFail = new CustomerDto();

        customerDtoSuccess.setFullName(userSuccess.getFullName());
        customerDtoSuccess.setAddress("Perum PU Bandung");
        customerDtoSuccess.setContact("0821123456");
        customerDtoSuccess.setEmail("just_fadhyl@hotmail.co.id");
        customerDtoSuccess.setGender("Cwk");
        customerDtoSuccess.setJob("Pengangguran");

        customerDtoFail.setContact("021212121");
        customerDtoFail.setGender("cwk");

    }

    @Test
    void validateUserData_shouldBeSuccess(){
        assertTrue(customerServiceImpl.validateUserData(userSuccess));
    }

    @Test
    void validateUserData_shouldBeFailed(){
        assertFalse(customerServiceImpl.validateUserData(userFailed));
    }

    @Test
    void validateUserData_shouldBeFailed_whenEmailIsNotRight(){
        userFailed.setUserName("admin");
        userFailed.setPassword("asal");
        userFailed.setEmail("gak bener ini");
        userFailed.setFullName("dieL");

        assertFalse(customerServiceImpl.validateUserData(userFailed));
    }

    @Test
    void register_shouldBeSuccess(){
        Boolean validateCheck = customerServiceImpl.validateUserData(userSuccess);
        List<Customer> customers = new ArrayList<>();

        if (validateCheck) {
            Customer customer = customerServiceImpl.registerUser(userSuccess);
            customers.add(customer);
        }

        when(customerRepository.findAll()).thenReturn(customers);
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void register_shouldBeFailed(){
        Boolean validateCheck = customerServiceImpl.validateUserData(userFailed);
        List<Customer> customers = new ArrayList<>();

        if (validateCheck) {
            Customer customer = customerServiceImpl.registerUser(userFailed);
            customers.add(customer);
        }

        when(customerRepository.findAll()).thenReturn(customers);
        assertNotEquals(1, customerRepository.findAll().size());
    }

    @Test
    void getByUsername_exist(){
//        customerRepository.save(customerService.registerUser(userSuccess));
//        System.out.println(modelMapper.map(userSuccess, Customer.class));
//        Optional<Customer> customerList = Optional.of(modelMapper.map(userSuccess, Customer.class));
//        given(customerRepository.findById("admin")).willReturn(customerList);
//        CustomerDto returned = customerService.getCustomer("admin");
//        verify(customerRepository).findById("admin");
//
//        assertNotNull(returned);
        customerDTO.setFullName("fadiel");
        when(modelMapper.map(any(), any())).thenReturn(customerDTO);
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customerEntity));
        CustomerDto result = customerServiceImpl.getCustomer("fadiel");

        assertEquals("fadiel", result.getFullName());
        assertNotNull(result);
    }

    @Test
    void getByUsername_notExist(){
//        customerRepository.save(customerService.registerUser(userSuccess));
//        System.out.println(modelMapper.map(userSuccess, Customer.class));
//        Optional<Customer> customerList = Optional.of(modelMapper.map(userSuccess, Customer.class));
//        given(customerRepository.findById("admin")).willReturn(customerList);
//        CustomerDto returned = customerService.getCustomer("admin");
//        verify(customerRepository).findById("admin");
//
//        assertNotNull(returned);
        customerDTO.setFullName("fadil");
        when(modelMapper.map(any(), any())).thenReturn(customerDTO);
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customerEntity));
        CustomerDto result = customerServiceImpl.getCustomer("fadiel");

        assertNotEquals("fadiel", result.getFullName());
        assertNotNull(result);
    }

    @Test
    void getAll() {
        Customer customer = customerServiceImpl.registerUser(userSuccess);
        customerDTO = modelMapper.map(customer, CustomerDto.class);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(customerDTO);

        when(customerServiceImpl.getAll()).thenReturn(customerDtoList);
        assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void update_shouldSuccess(){
//        customerEntity = modelMapper.map(customerDtoSuccess, Customer.class);
//        customerEntity.setUserName("fadiel");
//        customerRepository.save(customerEntity);
//        customerDtoSuccess.setAddress("Bandung Yahud");
//        Customer updated = customerService.update(customerEntity.getUserName(), customerDtoSuccess);
//
//        Optional<Customer> customerList = Optional.of((updated));
//        given(customerRepository.findById("fadiel")).willReturn(customerList);
//
//        assertEquals("Bandung Yahud", customerList.get().getAddress());
//        customerDtoSuccess.setAddress("Bandung Yahud");
        customerRepository.save(modelMapper.map(userSuccess, Customer.class));
//        System.out.println(customerRepository.findById("admin").get().getEmail());

//        when(customerService.update(customerEntity.getUserName(), customerDtoSuccess)).thenReturn(customerDtoSuccess);
//        customerDTO.setFullName("admin");
        Customer customerSuccess = new Customer();
        customerSuccess.setUserName("admin");
//        when(modelMapper.map(any(), any())).thenReturn(customerDtoSuccess);
        CustomerDto customer = customerService.update(customerSuccess.getUserName(), customerDtoSuccess);
//        when(customerServiceImpl.update("admin", customerDtoSuccess)).thenReturn(customerDtoSuccess);
//        System.out.println(customer.getAddress());
//        Optional<Customer> customerList = Optional.of((modelMapper.map(customer, Customer.class)));
//        given(customerRepository.findById("fadiel")).willReturn(customerList);
//
//        assertEquals("Bandung Yahud", customerList.get().getAddress());
    }

    void deleteById() {
    }
}