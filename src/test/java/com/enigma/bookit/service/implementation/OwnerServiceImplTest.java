//package com.enigma.bookit.service.implementation;
//
//import com.enigma.bookit.dto.OwnerDto;
//import com.enigma.bookit.dto.UserRegistrationDto;
//import com.enigma.bookit.dto.UserPasswordDto;
//import com.enigma.bookit.dto.UserSearchDto;
//import com.enigma.bookit.entity.user.Customer;
//import com.enigma.bookit.entity.user.Owner;
//import com.enigma.bookit.entity.user.User;
//import com.enigma.bookit.repository.OwnerRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class OwnerServiceImplTest {
//
//    @Autowired
//    OwnerServiceImpl ownerService;
//
//    @MockBean
//    OwnerRepository ownerRepository;
//
//    @Test
//    void register_shouldSave() {
//        Owner owner = new Owner();
//
//        owner.setId("usersuccess");
//        owner.setUserName("admin");
//        owner.setPassword("1234");
//        owner.setFullName("fadiel");
//        owner.setEmail("just_fadhyl@hotmail.co.id");
//        owner.setCreatedAt(LocalDateTime.now());
//        owner.setUpdatedAt(LocalDateTime.now());
//
//        User user = ownerService.convertEntityToUser(owner);
//        when(ownerRepository.save(owner)).thenReturn(owner);
//        UserRegistrationDto userRegistrationDto = ownerService.registerUser(user);
//
//        List<Owner> owners = new ArrayList<>();
//        owners.add(owner);
//
//        when(ownerRepository.findAll()).thenReturn(owners);
//        assertEquals(1, ownerRepository.findAll().size());
//        assertEquals(user.getId(), userRegistrationDto.getId());
//    }
//
//    @Test
//    void changePassword() {
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
//        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
//        UserPasswordDto userPasswordDto = new UserPasswordDto();
//        userPasswordDto.setPassword("passwordchanged");
//
//        User user = ownerService.convertEntityToUser(owner);
//        user.setPassword(userPasswordDto.getPassword());
//
//        when(ownerRepository.save(owner)).thenReturn(owner);
//        ownerService.changePassword(owner.getId(), userPasswordDto);
//
//        assertEquals("passwordchanged", ownerRepository.findById("usersuccess")
//                .get().getPassword());
//    }
//
//    @Test
//    void shouldUpdate() {
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
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId("usersuccess");
//        ownerDto.setFullName("dinny");
//        ownerDto.setEmail("dinny@gmail.com");
//        ownerDto.setAddress("Bandung Yahud");
//        ownerDto.setContact("082112345");
//        ownerDto.setGender("cwk");
//
//        when(ownerRepository.findById("usersuccess")).thenReturn(Optional.of(owner));
//        when(ownerRepository.save(owner)).thenReturn(owner);
//        ownerService.update("usersuccess", ownerDto);
//
//        when(ownerRepository.findById("usersuccess")).thenReturn(Optional.of(owner));
//        assertEquals("dinny", ownerRepository.findById("usersuccess").get().getFullName());
//        assertEquals("Bandung Yahud", ownerRepository.findById("usersuccess").get().getAddress());
//        assertEquals("cwk", ownerRepository.findById("usersuccess").get().getGender());
//    }
//
//    @Test
//    void shouldGetById() {
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
//        OwnerDto ownerDto = new OwnerDto();
//
//        ownerRepository.save(owner);
//        when(ownerRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
//        ownerDto.setId(ownerService.getById("usersuccess").getId());
//        ownerDto.setFullName(ownerService.getById("usersuccess").getFullName());
//        ownerDto.setEmail(ownerService.getById("usersuccess").getEmail());
//
//        assertEquals(owner.getId(), ownerDto.getId());
//        assertEquals(owner.getFullName(), ownerDto.getFullName());
//        assertEquals(owner.getEmail(), ownerDto.getEmail());
//    }
//
//    @Test
//    void shouldGetAll() {
//        Owner owner = new Owner();
//
//        owner.setId("usersuccess");
//        owner.setUserName("admin");
//        owner.setPassword("1234");
//        owner.setFullName("fadiel");
//        owner.setEmail("fadiel@gmail.com");
//        owner.setCreatedAt(LocalDateTime.now());
//        owner.setUpdatedAt(LocalDateTime.now());
//        ownerRepository.save(owner);
//
//        OwnerDto ownerDto = new OwnerDto();
//        ownerDto.setId(owner.getId());
//        ownerDto.setFullName(owner.getFullName());
//        ownerDto.setEmail(owner.getEmail());
//
//        List<Owner> owners = new ArrayList<>();
//        owners.add(owner);
//
//        when(ownerRepository.findAll()).thenReturn(owners);
//        List<OwnerDto> ownerDtos = ownerService.getAll();
//
//        assertEquals(1, ownerDtos.size());
//    }
//
//    @Test
//    void deleteById() {
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
//        ownerRepository.save(owner);
//        ownerService.deleteById("usersuccess");
//
//        assertEquals(0, ownerRepository.findAll().size());
//    }
//
//    @Test
//    void getOwnerPerPage(){
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
//        OwnerDto ownerDto = ownerService.convertEntityToDto(owner);
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
//        Page<Owner> ownerPage = new Page<Owner>() {
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
//            public <U> Page<U> map(Function<? super Owner, ? extends U> function) {
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
//            public List<Owner> getContent() {
//                return null;
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
//            public Iterator<Owner> iterator() {
//                return null;
//            }
//        };
//
//        when(ownerRepository.findAll((Specification<Owner>) any(), any())).thenReturn(ownerPage);
//        ownerRepository.save(owner);
//        when(ownerService.getCustomerPerPage(any(), eq(userSearchDto))).thenReturn(ownerDtoPage);
//
//        assertEquals("admin", ownerDtoPage.getContent().get(0).getUserName());
//    }
//}