package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.Customer;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import com.enigma.bookit.service.CustomerService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CustomerController.class)
@Import(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CustomerController customerController;

    @MockBean
    CustomerRepository customerRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test
    void registerCustomer_shouldSendSuccessResponse() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("testpassword123");
        user.setFullName("fadiel");
        user.setEmail("just_fadhyl@hotmail.co.id");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        when(customerService.registerUser(ArgumentMatchers.any())).thenReturn(userDto);

        mockMvc.perform(post(ApiUrlConstant.CUSTOMER)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.name())))
                .andExpect(jsonPath("$.data.id", is(userDto.getId())))
                .andExpect(jsonPath("$.data.userName", is(userDto.getUserName())))
                .andExpect(jsonPath("$.data.fullName", is(userDto.getFullName())));
    }

    @SneakyThrows
    @Test
    void registerCustomer_shouldSendFailedResponse() {
        User user = new User();

        user.setId("usersuccess");
        user.setFullName("fadiel");

        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        when(customerService.registerUser(ArgumentMatchers.any())).thenReturn(userDto);

        mockMvc.perform(post(ApiUrlConstant.CUSTOMER)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.BAD_REQUEST.name())));
    }

    @SneakyThrows
    @Test
    void getCustomerById_shouldSendSuccessResponse() {
        User user = new User();
        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("testpassword123");
        user.setFullName("fadiel");
        user.setEmail("just_fadhyl@hotmail.co.id");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(user.getId());
        customerDto.setFullName(user.getFullName());
        customerDto.setEmail(user.getEmail());
        customerDto.setJob("pengangguran");

        customerService.registerUser(user);
        when(customerService.getById(ArgumentMatchers.any())).thenReturn(customerDto);

        mockMvc.perform(get(ApiUrlConstant.CUSTOMER+"/"+customerDto.getId()))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message", is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data.id", is(customerDto.getId())))
                .andExpect(jsonPath("$.data.fullName", is(customerDto.getFullName())))
                .andExpect(jsonPath("$.data.email", is(customerDto.getEmail())))
                .andExpect(jsonPath("$.data.job", is(customerDto.getJob())));
    }

    @SneakyThrows
    @Test
    void getCustomerById_shouldSendFailedResponse()  {
        when(customerService.getById(ArgumentMatchers.any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.CUSTOMER+"/asalaja"))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void getAllCustomer_shouldSendSuccessResponse() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId("usersuccess");
        customerDto.setFullName("fadiel");
        customerDto.setEmail("dinny@gmail.com");
        customerDto.setJob("pengangguran");

        List<CustomerDto> customerDtos = new ArrayList<>();
        customerDtos.add(customerDto);

        when(customerService.getAll()).thenReturn(customerDtos);

        mockMvc.perform(get(ApiUrlConstant.CUSTOMER))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data[0].id", is(customerDto.getId())))
                .andExpect(jsonPath("$.data[0].fullName", is(customerDto.getFullName())))
                .andExpect(jsonPath("$.data[0].email", is(customerDto.getEmail())))
                .andExpect(jsonPath("$.data[0].job", is(customerDto.getJob())));
    }

    @SneakyThrows
    @Test
    void getAllCustomer_shouldSendFailedResponse()  {
        when(customerService.getAll()).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.CUSTOMER))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void updateCustomerDto_shouldSendSuccessResponse() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId("usersuccess");
        customerDto.setFullName("fadiel");
        customerDto.setEmail("dinny@gmail.com");
        customerDto.setJob("pengangguran");

        when(customerService.update(customerDto.getId(), customerDto)).thenReturn(customerDto);
        mockMvc.perform(put(ApiUrlConstant.CUSTOMER+"/"+customerDto.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customerDto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message", is(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data.id", is(customerDto.getId())))
                .andExpect(jsonPath("$.data.email", is(customerDto.getEmail())))
                .andExpect(jsonPath("$.data.fullName", is(customerDto.getFullName())))
                .andExpect(jsonPath("$.data.job", is(customerDto.getJob())));
    }

    @SneakyThrows
    @Test
    void updateCustomerDto_shouldSendFailedResponse() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId("usersuccess");
        customerDto.setFullName("fadiel");
        when(customerService.update(anyString(), ArgumentMatchers.any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(put(ApiUrlConstant.CUSTOMER+"/asalaja")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(customerDto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void changePassword_shouldSendSuccessMessage() {
        User user = new User();
        user.setId("ngehe");
        user.setUserName("fadiel");
        user.setFullName("gajadi2");
        user.setPassword("ngehe");
        user.setEmail("hehe@gmail.com");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("passwordchanged");

        when(customerService.changePassword(user.getId(), userPasswordDto)).thenReturn(userDto);
        customerService.changePassword("usersuccess", userPasswordDto);

        mockMvc.perform(put(ApiUrlConstant.CUSTOMER+"?id=ngehe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userPasswordDto)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL)))
                .andExpect(jsonPath("$.data.id",is(userDto.getId())));
    }

    @SneakyThrows
    @Test
    void changePassword_shouldSendFailedMessage(){
        User user = new User();
        user.setId("ngehe");
        user.setUserName("fadiel");
        user.setFullName("gajadi2");
        user.setPassword("ngehe");
        user.setEmail("hehe@gmail.com");

        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("passwordchanged");

        when(customerService.changePassword(user.getId(), userPasswordDto)).thenThrow(new NoSuchElementException());

        mockMvc.perform(put(ApiUrlConstant.CUSTOMER+"?id=ngehe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userPasswordDto)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void deleteCustomer(){
        User user = new User();
        user.setId("delete01");

        when(customerService.deleteById(user.getId())).thenReturn(true);

        mockMvc.perform(delete(ApiUrlConstant.CUSTOMER+"/id="+user.getId()))
                .andExpect(jsonPath("$.code",is(HttpStatus.GONE.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL)));
    }

    @SneakyThrows
    @Test
    void getCustomerPerPage_shouldReturnSuccessMessage(){
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

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setUserName(customer.getUserName());

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

        when(customerService.getCustomerPerPage(any(), any())).thenReturn(customerDtoPage);

        mockMvc.perform(get(ApiUrlConstant.CUSTOMER+"/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userSearchDto)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data[0].userName",is(userSearchDto.getSearchUserName())));
    }

    @SneakyThrows
    @Test
    void getCustomerPerPage_shouldReturnFailedMessage(){
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
        when(customerService.getCustomerPerPage(any(), any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.CUSTOMER+"/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userSearchDto)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

}