package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.CustomerRepository;
import com.enigma.bookit.service.CustomerService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Mock
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
        userDto.setUserName(user.getUserName());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        when(customerService.registerUser(ArgumentMatchers.any())).thenReturn(userDto);

        mockMvc.perform(post(ApiUrlConstant.CUSTOMER)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.name())))
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
                .andExpect(jsonPath("$.status", is(ErrorMessageConstant.CREATED_USER_FAILED)));
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
    void updateCustomerDto_shoulSendSuccessResponse() {
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
        userDto.setUserName(user.getUserName());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        when(customerService.changePassword(user.getId(), "jajaja")).thenReturn(userDto);

        mockMvc.perform(put(ApiUrlConstant.CUSTOMER+"?id=ngehe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(user.getPassword()).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL)));
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

        when(customerService.changePassword(anyString(), anyString())).thenThrow(new NoSuchElementException());

        mockMvc.perform(put(ApiUrlConstant.CUSTOMER+"?id=ngehe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(user.getPassword()).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void deleteCustomer_shouldSendSuccessMessage(){
        User user = new User();
        user.setId("delete01");

        customerService.registerUser(user);

        doNothing().when(customerService).deleteById(user.getId());

        mockMvc.perform(delete(ApiUrlConstant.CUSTOMER+"/id=delete01")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.GONE.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL)));
    }

}