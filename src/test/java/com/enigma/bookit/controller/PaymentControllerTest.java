package com.enigma.bookit.controller;

import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.security.WebSecurityConfig;
import com.enigma.bookit.security.jwt.AuthEntryPointJwt;
import com.enigma.bookit.security.jwt.JwtUtils;
import com.enigma.bookit.security.services.UserDetailsServiceImpl;
import com.enigma.bookit.service.PaymentService;
import com.enigma.bookit.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc
class PaymentControllerTest {

    @MockBean
    PaymentService paymentService;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    private Payment payment;
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup(){
        payment = new Payment();
        payment.setId("P01");
        Facility facility = new Facility();
        facility.setId("F01");
        payment.setFacility(facility);
        User user = new User();
        user.setId("C01");
        user.setEmail("test@gmail.com");
        payment.setUser(user);
        payment.setPaymentStatus("PENDING");
//        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaidAmount(BigDecimal.valueOf(1000));
//        payment.setBookingStart(LocalDateTime.now());
//        payment.setBookingEnd(LocalDateTime.now().plusHours(1));
        payment.setPackageChosen(PackageChosen.WEEKLY);
//        payment.setDueTime(LocalDateTime.now().plusHours(2));
    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_CUSTOMER")
    void createPayment() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setEmail("test@gmail.com");

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId("P01");


        when(paymentService.save(any(Payment.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));
        when(userService.getById(any(String.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(payment)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.externalId", Matchers.is(payment.getId())));
    }


    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_CUSTOMER")
    void getById() throws Exception {
        when(paymentService.getById(any(String.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));

        mockMvc.perform(get("/api/payment/{id}", payment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.is(payment.getId())));
    }

    @Test
    void deleteById() {

    }



    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_OWNER")
    void searchPaymentPerPage() throws Exception {
        payment = new Payment();
        payment.setId("P01");
        Facility facility = new Facility();
        facility.setId("F01");
        payment.setFacility(facility);
        User user = new User();
        user.setId("C01");
        user.setEmail("test@gmail.com");
        payment.setUser(user);
        payment.setPaidAmount(BigDecimal.valueOf(1000));

        PaymentSearchDTO paymentSearchDTO = new PaymentSearchDTO();
        paymentSearchDTO.setAmountStart(BigDecimal.valueOf(0));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setAmount(payment.getPaidAmount());

        List<PaymentDTO> paymentDTOS =  new ArrayList<>();
        paymentDTOS.add(paymentDTO);

        Page<PaymentDTO> paymentDTOPage = new Page<PaymentDTO>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super PaymentDTO, ? extends U> function) {
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
            public List<PaymentDTO> getContent() {
                return paymentDTOS;
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
            public Iterator<PaymentDTO> iterator() {
                return null;
            }
        };
        when(paymentService.getAllPerPage(any(), any())).thenReturn(paymentDTOPage);
        mockMvc.perform(get("/api/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(paymentSearchDTO)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data[0].id",is("P01")));

    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "fadiel123456", password = "mengontol", roles = "ROLE_CUSTOMER")
    void extendBook(){
        UserDto userDto = new UserDto();
        userDto.setId("C01");
        userDto.setEmail("test@gmail.com");

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId("P01");


        when(paymentService.save(any(Payment.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));
        when(userService.getById(any(String.class))).thenReturn(userDto);

        paymentDTO.setId("P01");
        paymentDTO.setAmount(BigDecimal.valueOf(10000));
        paymentDTO.setUser(modelMapper.map(userDto, User.class));
        Book book = new Book();
        book.setId("B01");
        book.setPayment(modelMapper.map(paymentDTO, Payment.class));

        PackageChosen packageChosen = PackageChosen.WEEKLY;

        when(paymentService.extendBook(any(String.class), any(PackageChosen.class))).thenReturn(paymentDTO);
        when(userService.getById(any(String.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/payment/extend/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(packageChosen)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }
}