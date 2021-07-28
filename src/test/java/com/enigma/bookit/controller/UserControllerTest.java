package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.UserRepository;
import com.enigma.bookit.service.UserService;
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

@WebMvcTest(controllers = UserController.class)
@Import(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @MockBean
    UserRepository userRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test
    void registerUser_shouldSendSuccessResponse() {
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

        when(userService.registerUser(ArgumentMatchers.any())).thenReturn(userDto);

        mockMvc.perform(post(ApiUrlConstant.USER)
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
    void registerUser_shouldSendFailedResponse() {
        User user = new User();

        user.setId("usersuccess");
        user.setFullName("fadiel");

        UserDto userRegistrationDto = new UserDto();
        userRegistrationDto.setUserName(user.getUserName());
        userRegistrationDto.setFullName(user.getFullName());
        userRegistrationDto.setEmail(user.getEmail());

        when(userService.registerUser(ArgumentMatchers.any())).thenReturn(userRegistrationDto);

        mockMvc.perform(post(ApiUrlConstant.USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.BAD_REQUEST.name())));
    }

    @SneakyThrows
    @Test
    void getUserById_shouldSendSuccessResponse() {
        User user = new User();
        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("testpassword123");
        user.setFullName("fadiel");
        user.setEmail("just_fadhyl@hotmail.co.id");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setJob("pengangguran");

        userService.registerUser(user);
        when(userService.getById(ArgumentMatchers.any())).thenReturn(userDto);

        mockMvc.perform(get(ApiUrlConstant.USER +"/"+ userDto.getId()))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message", is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data.id", is(userDto.getId())))
                .andExpect(jsonPath("$.data.fullName", is(userDto.getFullName())))
                .andExpect(jsonPath("$.data.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.data.job", is(userDto.getJob())));
    }

    @SneakyThrows
    @Test
    void getUserById_shouldSendFailedResponse()  {
        when(userService.getById(ArgumentMatchers.any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.USER +"/asalaja"))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void getAllUser_shouldSendSuccessResponse() {
        UserDto userDto = new UserDto();
        userDto.setId("usersuccess");
        userDto.setFullName("fadiel");
        userDto.setEmail("dinny@gmail.com");
        userDto.setJob("pengangguran");

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);

        when(userService.getAll()).thenReturn(userDtos);

        mockMvc.perform(get(ApiUrlConstant.USER))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data[0].id", is(userDto.getId())))
                .andExpect(jsonPath("$.data[0].fullName", is(userDto.getFullName())))
                .andExpect(jsonPath("$.data[0].email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.data[0].job", is(userDto.getJob())));
    }

    @SneakyThrows
    @Test
    void getAllUser_shouldSendFailedResponse()  {
        when(userService.getAll()).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.USER))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void updateUserDto_shouldSendSuccessResponse() {
        UserDto userDto = new UserDto();
        userDto.setId("usersuccess");
        userDto.setFullName("fadiel");
        userDto.setEmail("dinny@gmail.com");
        userDto.setJob("pengangguran");

        when(userService.update(userDto.getId(), userDto)).thenReturn(userDto);
        mockMvc.perform(put(ApiUrlConstant.USER +"/"+ userDto.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userDto)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message", is(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data.id", is(userDto.getId())))
                .andExpect(jsonPath("$.data.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.data.fullName", is(userDto.getFullName())))
                .andExpect(jsonPath("$.data.job", is(userDto.getJob())));
    }

    @SneakyThrows
    @Test
    void updateUserDto_shouldSendFailedResponse() {
        UserDto userDto = new UserDto();
        userDto.setId("usersuccess");
        userDto.setFullName("fadiel");
        when(userService.update(anyString(), ArgumentMatchers.any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(put(ApiUrlConstant.USER +"/asalaja")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userDto)).accept(MediaType.APPLICATION_JSON))
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

        when(userService.changePassword(user.getId(), userPasswordDto)).thenReturn(userDto);
        userService.changePassword("usersuccess", userPasswordDto);

        mockMvc.perform(put(ApiUrlConstant.USER +"?id=ngehe")
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

        when(userService.changePassword(user.getId(), userPasswordDto)).thenThrow(new NoSuchElementException());

        mockMvc.perform(put(ApiUrlConstant.USER +"?id=ngehe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userPasswordDto)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

    @SneakyThrows
    @Test
    void deleteUser(){
        User user = new User();
        user.setId("delete01");

        when(userService.deleteById(user.getId())).thenReturn(true);

        mockMvc.perform(delete(ApiUrlConstant.USER +"/id="+user.getId()))
                .andExpect(jsonPath("$.code",is(HttpStatus.GONE.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL)));
    }

    @SneakyThrows
    @Test
    void getUserPerPage_shouldReturnSuccessMessage(){
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setSearchUserName(user.getUserName());
        userSearchDto.setSearchFullName(user.getFullName());

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);

        Page<UserDto> customerDtoPage = new Page<UserDto>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super UserDto, ? extends U> function) {
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
            public List<UserDto> getContent() {
                return userDtos;
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
            public Iterator<UserDto> iterator() {
                return null;
            }
        };

        when(userService.getCustomerPerPage(any(), any())).thenReturn(customerDtoPage);

        mockMvc.perform(get(ApiUrlConstant.USER +"/search")
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
    void getUserPerPage_shouldReturnFailedMessage(){
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setSearchUserName(user.getUserName());
        userSearchDto.setSearchFullName(user.getFullName());
        when(userService.getCustomerPerPage(any(), any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(get(ApiUrlConstant.USER +"/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(userSearchDto)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
    }

}