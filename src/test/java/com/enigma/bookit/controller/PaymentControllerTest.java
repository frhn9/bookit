package com.enigma.bookit.controller;

import com.enigma.bookit.dto.CustomerDto;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.entity.Customer;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @MockBean
    PaymentService paymentService;

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    private Payment payment;
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup(){
        payment = new Payment();
        payment.setId("P01");
        Facility facility = new Facility();
        facility.setId("F01");
        payment.setFacility(facility);
        Customer customer = new Customer();
        customer.setId("C01");
        customer.setEmail("test@gmail.com");
        payment.setCustomer(customer);
        payment.setPaymentStatus(false);
//        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(BigDecimal.valueOf(1000));
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
    void createPayment() throws Exception {

        when(paymentService.save(any(Payment.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));

        mockMvc.perform(post("/api/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(payment)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", Matchers.is(payment.getId())));
    }

    @Test
    void payPayment() throws Exception {
        when(paymentService.pay(any(String.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));

        mockMvc.perform(put("/api/payment/pay/{id}", payment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(payment)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.is(payment.getId())));
    }

    @Test
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
    void payXendit() throws Exception {
        payment = new Payment();
        payment.setId("P01");
        Facility facility = new Facility();
        facility.setId("F01");
        payment.setFacility(facility);
        Customer customer = new Customer();
        customer.setId("C01");
        customer.setEmail("test@gmail.com");
        payment.setCustomer(customer);
        payment.setAmount(BigDecimal.valueOf(1000));
        payment.setPackageChosen(PackageChosen.WEEKLY);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId("C01");
        customerDto.setEmail("test@gmail.com");
        when(paymentService.getById(any(String.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));
        when(paymentService.pay(any(String.class))).thenReturn(modelMapper.map(payment, PaymentDTO.class));
        when(customerService.getById(any(String.class))).thenReturn(customerDto);
        mockMvc.perform(post("/api/payment/payXendit/{id}", payment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.id", Matchers.is(payment.getId())));
    }
}