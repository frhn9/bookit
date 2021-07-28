//package com.enigma.bookit.controller;
//
//import com.enigma.bookit.constant.ApiUrlConstant;
//import com.enigma.bookit.constant.ErrorMessageConstant;
//import com.enigma.bookit.constant.SuccessMessageConstant;
//import com.enigma.bookit.dto.OwnerDto;
//import com.enigma.bookit.dto.UserRegistrationDto;
//import com.enigma.bookit.dto.UserPasswordDto;
//import com.enigma.bookit.dto.UserSearchDto;
//import com.enigma.bookit.entity.user.Owner;
//import com.enigma.bookit.entity.user.User;
//import com.enigma.bookit.service.OwnerService;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.function.Function;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(controllers = OwnerController.class)
//@Import(OwnerController.class)
//class OwnerControllerTest {
//
//    @MockBean
//    OwnerService ownerService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    OwnerController ownerController;
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS).writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @SneakyThrows
//    @Test
//    void registerOwner_shouldSendSuccessResponse() {
//        User user = new User();
//
//        user.setId("usersuccess");
//        user.setUserName("admin");
//        user.setPassword("testpassword123");
//        user.setFullName("fadiel");
//        user.setEmail("just_fadhyl@hotmail.co.id");
//
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
//        userRegistrationDto.setId(user.getId());
//        userRegistrationDto.setUserName(user.getUserName());
//        userRegistrationDto.setFullName(user.getFullName());
//        userRegistrationDto.setEmail(user.getEmail());
//
//        when(ownerService.registerUser(ArgumentMatchers.any())).thenReturn(userRegistrationDto);
//
//        mockMvc.perform(post(ApiUrlConstant.OWNER)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(user)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.CREATED.value())))
//                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.name())))
//                .andExpect(jsonPath("$.data.userName", is(userRegistrationDto.getUserName())))
//                .andExpect(jsonPath("$.data.fullName", is(userRegistrationDto.getFullName())));
//    }
//
//    @SneakyThrows
//    @Test
//    void registerOwner_shouldSendFailedResponse() {
//        User user = new User();
//
//        user.setId("usersuccess");
//        user.setFullName("fadiel");
//
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
//        userRegistrationDto.setUserName(user.getUserName());
//        userRegistrationDto.setFullName(user.getFullName());
//        userRegistrationDto.setEmail(user.getEmail());
//
//        when(ownerService.registerUser(ArgumentMatchers.any())).thenReturn(userRegistrationDto);
//
//        mockMvc.perform(post(ApiUrlConstant.OWNER)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(user)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.BAD_REQUEST.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.BAD_REQUEST.name())));
//    }
//
//    @SneakyThrows
//    @Test
//    void getOwnerById_shouldSendSuccessResponse() {
//        User user = new User();
//        user.setId("usersuccess");
//        user.setUserName("admin");
//        user.setPassword("testpassword123");
//        user.setFullName("fadiel");
//        user.setEmail("just_fadhyl@hotmail.co.id");
//
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId(user.getId());
//        ownerDto.setFullName(user.getFullName());
//        ownerDto.setEmail(user.getEmail());
//
//        ownerService.registerUser(user);
//        when(ownerService.getById(ArgumentMatchers.any())).thenReturn(ownerDto);
//
//        mockMvc.perform(get(ApiUrlConstant.OWNER+"/"+ownerDto.getId()))
//                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
//                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
//                .andExpect(jsonPath("$.message", is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
//                .andExpect(jsonPath("$.data.id", is(ownerDto.getId())))
//                .andExpect(jsonPath("$.data.fullName", is(ownerDto.getFullName())))
//                .andExpect(jsonPath("$.data.email", is(ownerDto.getEmail())));
//    }
//
//    @SneakyThrows
//    @Test
//    void getOwnerById_shouldSendFailedResponse() {
//        when(ownerService.getById(ArgumentMatchers.any())).thenThrow(new NoSuchElementException());
//
//        mockMvc.perform(get(ApiUrlConstant.OWNER+"/asalaja"))
//                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
//    }
//
//    @SneakyThrows
//    @Test
//    void getAllOwner_shouldSendSuccessResponse() {
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId("usersuccess");
//        ownerDto.setFullName("fadiel");
//        ownerDto.setEmail("dinny@gmail.com");
//
//        List<OwnerDto> ownerDtos = new ArrayList<>();
//        ownerDtos.add(ownerDto);
//
//        when(ownerService.getAll()).thenReturn(ownerDtos);
//
//        mockMvc.perform(get(ApiUrlConstant.OWNER))
//                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
//                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
//                .andExpect(jsonPath("$.data[0].id", is(ownerDto.getId())))
//                .andExpect(jsonPath("$.data[0].fullName", is(ownerDto.getFullName())))
//                .andExpect(jsonPath("$.data[0].email", is(ownerDto.getEmail())));
//    }
//
//    @SneakyThrows
//    @Test
//    void getAllOwner_shouldSendFailedResponse() {
//        when(ownerService.getAll()).thenThrow(new NoSuchElementException());
//
//        mockMvc.perform(get(ApiUrlConstant.OWNER))
//                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
//    }
//
//    @SneakyThrows
//    @Test
//    void updateOwnerDto_shouldSendSuccessResponse() {
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId("usersuccess");
//        ownerDto.setFullName("fadiel");
//        ownerDto.setEmail("dinny@gmail.com");
//
//        when(ownerService.update(ownerDto.getId(), ownerDto)).thenReturn(ownerDto);
//        mockMvc.perform(put(ApiUrlConstant.OWNER+"/"+ownerDto.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(ownerDto)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
//                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
//                .andExpect(jsonPath("$.message", is(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL)))
//                .andExpect(jsonPath("$.data.id", is(ownerDto.getId())))
//                .andExpect(jsonPath("$.data.email", is(ownerDto.getEmail())))
//                .andExpect(jsonPath("$.data.fullName", is(ownerDto.getFullName())));
//    }
//
//    @SneakyThrows
//    @Test
//    void updateOwnerDto_shouldSendFailedResponse() {
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId("usersuccess");
//        ownerDto.setFullName("fadiel");
//        when(ownerService.update(anyString(), ArgumentMatchers.any())).thenThrow(new NoSuchElementException());
//
//        mockMvc.perform(put(ApiUrlConstant.OWNER+"/asalaja")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(ownerDto)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message", is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
//    }
//
//    @SneakyThrows
//    @Test
//    void changePassword_shouldSendSuccessResponse() {
//        User user = new User();
//        user.setId("ngehe");
//        user.setUserName("fadiel");
//        user.setFullName("gajadi2");
//        user.setPassword("ngehe");
//        user.setEmail("hehe@gmail.com");
//
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
//        userRegistrationDto.setId(user.getId());
//        userRegistrationDto.setUserName(user.getUserName());
//        userRegistrationDto.setFullName(user.getFullName());
//        userRegistrationDto.setEmail(user.getEmail());
//
//        UserPasswordDto userPasswordDto = new UserPasswordDto();
//        userPasswordDto.setPassword("passwordchanged");
//
//        when(ownerService.changePassword(user.getId(), userPasswordDto)).thenReturn(userRegistrationDto);
//
//        mockMvc.perform(put(ApiUrlConstant.OWNER+"?id=ngehe")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(userPasswordDto)).accept(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
//                .andExpect(jsonPath("$.status", is(HttpStatus.OK.name())))
//                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.CHANGE_PASSWORD_SUCCESSFUL)));
//    }
//
//    @SneakyThrows
//    @Test
//    void changePassword_shouldSendFailedResponse(){
//        User user = new User();
//        user.setId("ngehe");
//        user.setUserName("fadiel");
//        user.setFullName("gajadi2");
//        user.setPassword("ngehe");
//        user.setEmail("hehe@gmail.com");
//
//        UserPasswordDto userPasswordDto = new UserPasswordDto();
//        userPasswordDto.setPassword("passwordchanged");
//
//        when(ownerService.changePassword(user.getId(), userPasswordDto)).thenThrow(new NoSuchElementException());
//
//        mockMvc.perform(put(ApiUrlConstant.OWNER+"?id=ngehe")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(userPasswordDto)).accept(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
//    }
//
//    @SneakyThrows
//    @Test
//    void deleteOwner() {
//        User user = new User();
//        user.setId("delete01");
//
//        when(ownerService.deleteById(user.getId())).thenReturn(false);
//
//        mockMvc.perform(delete(ApiUrlConstant.OWNER+"/id=delete01"))
//                .andExpect(jsonPath("$.code",is(HttpStatus.GONE.value())))
//                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())))
//                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL)));
//    }
//
//    @SneakyThrows
//    @Test
//    void getOwnerPerPage_shouldReturnFailedMessage(){
//        Owner owner = new Owner();
//
//        owner.setId("usersuccess");
//        owner.setUserName("admin");
//        owner.setPassword("1234");
//        owner.setFullName("fadiel");
//        owner.setEmail("fadiel@gmail.com");
//        owner.setCreatedAt(LocalDateTime.now());
//        owner.setUpdatedAt(LocalDateTime.now());
//
//        UserSearchDto userSearchDto = new UserSearchDto();
//        userSearchDto.setSearchUserName(owner.getUserName());
//        userSearchDto.setSearchFullName(owner.getFullName());
//
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId(owner.getId());
//        ownerDto.setUserName(owner.getUserName());
//
//        List<OwnerDto> ownerDtos = new ArrayList<>();
//        ownerDtos.add(ownerDto);
//
//        Page<OwnerDto> ownerDtoPage = new Page<OwnerDto>() {
//            @Override
//            public int getTotalPages() {
//                return 0;
//            }
//
//            @Override
//            public long getTotalElements() {
//                return 0;
//            }
//
//            @Override
//            public <U> Page<U> map(Function<? super OwnerDto, ? extends U> function) {
//                return null;
//            }
//
//            @Override
//            public int getNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getSize() {
//                return 0;
//            }
//
//            @Override
//            public int getNumberOfElements() {
//                return 0;
//            }
//
//            @Override
//            public List<OwnerDto> getContent() {
//                return ownerDtos;
//            }
//
//            @Override
//            public boolean hasContent() {
//                return false;
//            }
//
//            @Override
//            public Sort getSort() {
//                return null;
//            }
//
//            @Override
//            public boolean isFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//
//            @Override
//            public boolean hasNext() {
//                return false;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//
//            @Override
//            public Pageable nextPageable() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousPageable() {
//                return null;
//            }
//
//            @Override
//            public Iterator<OwnerDto> iterator() {
//                return null;
//            }
//        };
//
//        when(ownerService.getCustomerPerPage(any(), any())).thenReturn(ownerDtoPage);
//
//        mockMvc.perform(get(ApiUrlConstant.OWNER+"/search")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(userSearchDto)).accept(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
//                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
//                .andExpect(jsonPath("$.data[0].userName",is(userSearchDto.getSearchUserName())));
//    }
//
//    @SneakyThrows
//    @Test
//    void getCustomerPerPage_shouldReturnFailedMessage(){
//        Owner owner = new Owner();
//
//        owner.setId("usersuccess");
//        owner.setUserName("admin");
//        owner.setPassword("1234");
//        owner.setFullName("fadiel");
//        owner.setEmail("fadiel@gmail.com");
//        owner.setCreatedAt(LocalDateTime.now());
//        owner.setUpdatedAt(LocalDateTime.now());
//
//        UserSearchDto userSearchDto = new UserSearchDto();
//        userSearchDto.setSearchUserName(owner.getUserName());
//        userSearchDto.setSearchFullName(owner.getFullName());
//
//        when(ownerService.getCustomerPerPage(any(), any())).thenThrow(new NoSuchElementException());
//
//        mockMvc.perform(get(ApiUrlConstant.OWNER+"/search")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(userSearchDto)).accept(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code",is(HttpStatus.NOT_FOUND.value())))
//                .andExpect(jsonPath("$.status",is(HttpStatus.NOT_FOUND.name())))
//                .andExpect(jsonPath("$.message",is(ErrorMessageConstant.GET_OR_UPDATE_DATA_FAILED)));
//    }
//}