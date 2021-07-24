package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.entity.user.Owner;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.OwnerRepository;
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
class OwnerServiceImplTest {

    @Autowired
    OwnerServiceImpl ownerService;

    @MockBean
    OwnerRepository ownerRepository;

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

        ownerService.registerUser(user);
        ownerRepository.save(ownerService.convertUserToEntity(user));
        List<User> users = new ArrayList<>();
        users.add(user);

        Owner owner = ownerService.convertUserToEntity(user);
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);

        when(ownerRepository.findAll()).thenReturn(owners);
        assertEquals(1, ownerRepository.findAll().size());
    }

    @Test
    void changePassword() {
        Owner owner = new Owner();

        owner.setId("usersuccess");
        owner.setUserName("admin");
        owner.setPassword("1234");
        owner.setFullName("fadiel");
        owner.setEmail("fadiel@gmail.com");
        owner.setCreatedAt(LocalDateTime.now());
        owner.setUpdatedAt(LocalDateTime.now());

        ownerRepository.save(owner);
        when(ownerRepository.findById("usersuccess")).thenReturn(Optional.of(owner));
        ownerService.changePassword("usersuccess", "passwordchanged");

        assertEquals("passwordchanged", ownerRepository.findById("usersuccess")
                .get().getPassword());
    }

    @Test
    void shouldUpdate() {
        Owner owner = new Owner();

        owner.setId("usersuccess");
        owner.setUserName("admin");
        owner.setPassword("1234");
        owner.setFullName("fadiel");
        owner.setEmail("fadiel@gmail.com");
        owner.setCreatedAt(LocalDateTime.now());
        owner.setUpdatedAt(LocalDateTime.now());

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId("usersuccess");
        ownerDto.setFullName("dinny");
        ownerDto.setEmail("dinny@gmail.com");
        ownerDto.setAddress("Bandung Yahud");
        ownerDto.setContact("082112345");
        ownerDto.setGender("cwk");

        ownerRepository.save(owner);
        when(ownerRepository.findById("usersuccess")).thenReturn(Optional.of(owner));
        ownerService.update("usersuccess", ownerDto);

        when(ownerRepository.findById("usersuccess")).thenReturn(Optional.of(owner));
        assertEquals("dinny", ownerRepository.findById("usersuccess").get().getFullName());
        assertEquals("Bandung Yahud", ownerRepository.findById("usersuccess").get().getAddress());
        assertEquals("cwk", ownerRepository.findById("usersuccess").get().getGender());
    }

    @Test
    void shouldGetById() {
        Owner owner = new Owner();

        owner.setId("usersuccess");
        owner.setUserName("admin");
        owner.setPassword("1234");
        owner.setFullName("fadiel");
        owner.setEmail("fadiel@gmail.com");
        owner.setCreatedAt(LocalDateTime.now());
        owner.setUpdatedAt(LocalDateTime.now());

        OwnerDto ownerDto = new OwnerDto();

        ownerRepository.save(owner);
        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        ownerDto.setId(ownerService.getById("usersuccess").getId());
        ownerDto.setFullName(ownerService.getById("usersuccess").getFullName());
        ownerDto.setEmail(ownerService.getById("usersuccess").getEmail());

        assertEquals(owner.getId(), ownerDto.getId());
        assertEquals(owner.getFullName(), ownerDto.getFullName());
        assertEquals(owner.getEmail(), ownerDto.getEmail());
    }

    @Test
    void shouldGetAll() {
        Owner owner = new Owner();

        owner.setId("usersuccess");
        owner.setUserName("admin");
        owner.setPassword("1234");
        owner.setFullName("fadiel");
        owner.setEmail("fadiel@gmail.com");
        owner.setCreatedAt(LocalDateTime.now());
        owner.setUpdatedAt(LocalDateTime.now());
        ownerRepository.save(owner);

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setFullName(owner.getFullName());
        ownerDto.setEmail(owner.getEmail());

        List<Owner> owners = new ArrayList<>();
        owners.add(owner);

        when(ownerRepository.findAll()).thenReturn(owners);
        List<OwnerDto> ownerDtos = ownerService.getAll();

        assertEquals(1, ownerDtos.size());
    }

    @Test
    void deleteById() {
        Owner owner = new Owner();

        owner.setId("usersuccess");
        owner.setUserName("admin");
        owner.setPassword("1234");
        owner.setFullName("fadiel");
        owner.setEmail("fadiel@gmail.com");
        owner.setCreatedAt(LocalDateTime.now());
        owner.setUpdatedAt(LocalDateTime.now());

        ownerRepository.save(owner);
        ownerService.deleteById("usersuccess");

        assertEquals(0, ownerRepository.findAll().size());
    }

}