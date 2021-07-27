package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void register_shouldSave() {
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("just_fadhyl@hotmail.co.id");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        User user = customerService.convertEntityToUser(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        UserDto userDto = customerService.registerUser(user);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);
        assertEquals(1, customerRepository.findAll().size());
        assertEquals(user.getId(), userDto.getId());
    }

    @Test
    void changePassword() {
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("fadiel@gmail.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("passwordchanged");

        User user = customerService.convertEntityToUser(customer);
        user.setPassword(userPasswordDto.getPassword());

        when(customerRepository.save(customer)).thenReturn(customer);
        customerService.changePassword(customer.getId(), userPasswordDto);

        assertEquals("passwordchanged", customerRepository.findById("usersuccess")
                    .get().getPassword());
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

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName("dinny");
        customerDto.setEmail("dinny@gmail.com");
        customerDto.setAddress("Bandung Yahud");
        customerDto.setContact("082112345");
        customerDto.setGender("cwk");
        customerDto.setJob("nganggur bos");

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        customerService.update(customerDto.getId(), customerDto);

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

        customerRepository.save(customer);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        customerService.deleteById("usersuccess");

        assertEquals(0, customerRepository.findAll().size());
    }

    @Test
    void getCustomerPerPage(){
        Customer customer = new Customer();

        customer.setId("usersuccess");
        customer.setUserName("admin");
        customer.setPassword("1234");
        customer.setFullName("fadiel");
        customer.setEmail("fadiel@gmail.com");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setSearchUserName(customer.getUserName());
        userSearchDto.setSearchFullName(customer.getFullName());

        CustomerDto customerDto = customerService.convertEntityToDto(customer);
        List<CustomerDto> customerDtos = new ArrayList<>();
        customerDtos.add(customerDto);

        Page<CustomerDto> customerDtoPage = new Page<CustomerDto>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super CustomerDto, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<CustomerDto> getContent() {
                return customerDtos;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<CustomerDto> iterator() {
                return null;
            }
        };

        Page<Customer> customerPage = new Page<Customer>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Customer, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Customer> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Customer> iterator() {
                return null;
            }
        };

        when(customerRepository.findAll((Specification<Customer>) any(), any())).thenReturn(customerPage);
        customerRepository.save(customer);
        when(customerService.getCustomerPerPage(any(), eq(userSearchDto))).thenReturn(customerDtoPage);

        assertEquals("admin", customerDtoPage.getContent().get(0).getUserName());
    }

}