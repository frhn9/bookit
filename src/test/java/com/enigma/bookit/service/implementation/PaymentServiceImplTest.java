package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ResponseMessage;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.entity.Customer;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.BookRepository;
import com.enigma.bookit.repository.CustomerRepository;
import com.enigma.bookit.repository.FacilityRepository;
import com.enigma.bookit.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentServiceImplTest {

    @Spy
    RestTemplate restTemplate;

    @Autowired
    PaymentServiceImpl paymentService;

    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    FacilityRepository facilityRepository;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    BookRepository bookRepository;

    private Payment payment = new Payment();
    private Customer customer = new Customer();
    private Facility facility = new Facility();

    @BeforeEach
    void setup(){
        customer.setId("C01");
        customer.setContact("0812345");
        customerRepository.save(customer);
        when(customerRepository.findById("C01")).thenReturn(java.util.Optional.ofNullable(customer));

        facility.setId("F01");
        facility.setContact("0854321");
        facility.setCapacity(5);
        facility.setRentPriceOnce(BigDecimal.valueOf(100));
        facilityRepository.save(facility);
        when(facilityRepository.findById("F01")).thenReturn(java.util.Optional.ofNullable(facility));

        payment.setId("P01");
        payment.setPaymentStatus(false);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setCustomer(customer);
        payment.setFacility(facility);
        payment.setBookingStart(LocalDateTime.now());
        payment.setDueTime(LocalDateTime.now().plusHours(2));
        paymentRepository.save(payment);
        when(paymentRepository.findById("P01")).thenReturn(java.util.Optional.ofNullable(payment));
    }

    @Test
    void should_save_Once() {
        payment.setPackageChosen(PackageChosen.ONCE);
        payment.setBookingEnd(LocalDateTime.now().plusHours(5));
        when(facilityRepository.findById(facility.getId())).thenReturn(java.util.Optional.ofNullable(facility));
        List<Integer> booked = new ArrayList<>();
        booked.add(1);
        when(bookRepository.countCap(facility.getId(), payment.getBookingStart(), payment.getBookingEnd())).thenReturn(booked);
        PaymentDTO output = paymentService.save(payment);
        assertEquals(payment.getId(), output.getId());
    }

    @Test
    void should_save_Weekly() {
        payment.setPackageChosen(PackageChosen.WEEKLY);
        when(facilityRepository.findById(facility.getId())).thenReturn(java.util.Optional.ofNullable(facility));
        List<Integer> booked = new ArrayList<>();
        booked.add(1);
        when(bookRepository.countCap(facility.getId(), payment.getBookingStart(), payment.getBookingEnd())).thenReturn(booked);
        PaymentDTO output = paymentService.save(payment);
        assertEquals(payment.getId(), output.getId());
    }

    @Test
    void should_save_Monthly() {
        payment.setPackageChosen(PackageChosen.MONTHLY);
        when(facilityRepository.findById(facility.getId())).thenReturn(java.util.Optional.ofNullable(facility));
        List<Integer> booked = new ArrayList<>();
        booked.add(1);
        when(bookRepository.countCap(facility.getId(), payment.getBookingStart(), payment.getBookingEnd())).thenReturn(booked);
        PaymentDTO output = paymentService.save(payment);
        assertEquals(payment.getId(), output.getId());
    }

    @Test
    void getById() {
        payment = new Payment();
        PaymentDTO output = new PaymentDTO();
        payment.setId("P01");
        paymentRepository.save(payment);
        when(paymentRepository.findById(payment.getId())).thenReturn(java.util.Optional.ofNullable(payment));
        output = paymentService.getById(payment.getId());
        assertEquals(payment.getId(), output.getId());
    }

    @Test
    void deleteById() {
    }

    @Test
    void pay() {
        when(paymentRepository.findById(payment.getId())).thenReturn(java.util.Optional.ofNullable(payment));
        Payment output = new Payment();
        output.setId(payment.getId());
        String url = "http://localhost:8081/transfer";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", facility.getContact())
                .queryParam("receiver", customer.getContact())
                .queryParam("amount", 100);
        when(restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class))
                .thenReturn(null);
        payment.setAmount(BigDecimal.valueOf(1000));
        when(paymentRepository.save(payment)).thenReturn(payment);
        assertEquals(output.getId(), paymentService.pay(payment.getId()).getId());
    }

    @Test
    void getAllPerPage() {
    }

    @Test
    void checkFacilityCapacity() {
        when(facilityRepository.getById(facility.getId())).thenReturn(facility);
        List<Integer> booked = new ArrayList<>();
        booked.add(1);
        when(bookRepository.countCap(facility.getId(), payment.getBookingStart(), payment.getBookingEnd())).thenReturn(booked);
//        doNothing().when(paymentService).checkFacilityCapacity(payment);
        paymentService.checkFacilityCapacity(payment);
    }

    @Test
    void convertPaymentToPaymentDTO() {
    }


}