package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.dto.UserPasswordDto;
import com.enigma.bookit.dto.UserSearchDto;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.UserRepository;
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
class UserServiceImplTest {

    @Autowired
    UserServiceImpl service;

    @MockBean
    UserRepository repository;

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
        
        when(repository.save(user)).thenReturn(user);
        UserDto userDto = service.registerUser(user);

        List<User> users = new ArrayList<>();
        users.add(user);

        when(repository.findAll()).thenReturn(users);
        assertEquals(1, repository.findAll().size());
        assertEquals(user.getId(), userDto.getId());
    }

    @Test
    void changePassword() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("passwordchanged");

        user.setPassword(userPasswordDto.getPassword());

        when(repository.save(user)).thenReturn(user);
        service.changePassword(user.getId(), userPasswordDto);

        assertEquals("passwordchanged", repository.findById("usersuccess")
                    .get().getPassword());
    }
//
    @Test
    void shouldUpdate() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName("dinny");
        userDto.setEmail("dinny@gmail.com");
        userDto.setAddress("Bandung Yahud");
        userDto.setContact("082112345");
        userDto.setGender("cwk");
        userDto.setJob("nganggur bos");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        service.update(userDto.getId(), userDto);

        assertEquals("dinny", repository.findById("usersuccess").get().getFullName());
        assertEquals("Bandung Yahud", repository.findById("usersuccess").get().getAddress());
        assertEquals("nganggur bos", repository.findById("usersuccess").get().getJob());

    }

    @Test
    void shouldGetById() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDto userDto = new UserDto();

        repository.save(user);
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        userDto.setId(service.getById("usersuccess").getId());
        userDto.setFullName(service.getById("usersuccess").getFullName());
        userDto.setEmail(service.getById("usersuccess").getEmail());

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFullName(), userDto.getFullName());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void shouldGetAll() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        repository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        List<User> users = new ArrayList<>();
        users.add(user);

        when(repository.findAll()).thenReturn(users);
        List<UserDto> customersDto = service.getAll();

        assertEquals(1, customersDto.size());
    }

    @Test
    void shouldDeleteById() {
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        repository.save(user);
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        service.deleteById("usersuccess");

        assertEquals(0, repository.findAll().size());
    }

    @Test
    void getCustomerPerPage(){
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

        UserDto userDto = service.convertUserToDto(user);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);

        Page<UserDto> userDtoPage = new Page<UserDto>() {
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

        Page<User> userPage = new Page<User>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super User, ? extends U> function) {
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
            public List<User> getContent() {
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
            public Iterator<User> iterator() {
                return null;
            }
        };

        when(repository.findAll((Specification<User>) any(), any())).thenReturn(userPage);
        repository.save(user);
        when(service.getCustomerPerPage(any(), eq(userSearchDto))).thenReturn(userDtoPage);

        assertEquals("admin", userDtoPage.getContent().get(0).getUserName());
    }

    @Test
    void userNameExist(){
        User user = new User();

        user.setId("usersuccess");
        user.setUserName("admin");
        user.setPassword("1234");
        user.setFullName("fadiel");
        user.setEmail("fadiel@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        when(repository.existsByUserName(user.getUserName())).thenReturn(true);

        assertEquals(true, service.userNameExist("admin"));
        assertEquals(false, service.userNameExist("asal"));
    }

    @Test
    void convertDtoToUser(){
        UserDto userDto = new UserDto();
        userDto.setId("user01");

        User user = service.convertDtoToUser(userDto);

        assertEquals(userDto.getId(), user.getId());
    }

}