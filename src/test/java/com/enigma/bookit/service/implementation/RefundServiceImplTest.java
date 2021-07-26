package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.*;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RefundServiceImplTest {

    @Autowired
    RefundServiceImpl service;

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    PaymentServiceImpl paymentService;

    @Spy
    RestTemplate restTemplate;

    @MockBean
    RefundRepository refundRepository;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    FacilityRepository facilityRepository;

    @MockBean
    CustomerRepository customerRepository;

    @Autowired
    EntityManager entityManager;

    private Refund refund = new Refund();
    private Book book =  new Book();
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
        facilityRepository.save(facility);
        when(facilityRepository.findById("F01")).thenReturn(java.util.Optional.ofNullable(facility));

        payment.setId("P01");
        payment.setPaymentStatus(false);
        payment.setPackageChosen(PackageChosen.WEEKLY);
        payment.setPaymentDate(new Timestamp(new Date().getTime()));
        payment.setCustomer(customer);
        payment.setFacility(facility);
        payment.setBookingStart(new Timestamp(new Date().getTime()));
        payment.setBookingEnd(new Timestamp(new Date().getTime() + TimeUnit.DAYS.toMillis(7)));
        payment.setDueTime(new Timestamp(new Date().getTime() + TimeUnit.HOURS.toMillis(2)));
        paymentRepository.save(payment);
        when(paymentRepository.findById("P01")).thenReturn(java.util.Optional.ofNullable(payment));

        book.setId("B01");
        book.setActiveUntil(new Timestamp(new Date().getTime() + TimeUnit.DAYS.toMillis(8)));
        book.setActiveFrom(new Timestamp(new Date().getTime()));
        book.setPayment(payment);
        bookRepository.save(book);
        when(bookRepository.findById("B01")).thenReturn(java.util.Optional.of(book));

        refund.setId("R01");
        refund.setBook(book);
        refund.setRequestRefundTime(new Timestamp(System.currentTimeMillis()));
        refund.setRefundTime(new Timestamp(System.currentTimeMillis()));
        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
        refund.setStatus(false);
        refundRepository.save(refund);


    }

    @Test
    void applyRefund() {
//        Book book = new Book();
//        book.setId("B01");
//        book.setActiveUntil(new Timestamp(new Date().getTime() + TimeUnit.DAYS.toMillis(8)));
//        book.setActiveFrom(new Timestamp(new Date().getTime()));
//        bookRepository.save(book);
//        when(bookRepository.findById("B01")).thenReturn(java.util.Optional.of(book));
        when(refundRepository.save(refund)).thenReturn(refund);
        assertEquals(refund, service.applyRefund(refund));
    }

    @Test
    void checkRefundStatus(){
//        refund.setId("R01");
//        refund.setBook(book);
//        refund.setRequestRefundTime(new Timestamp(System.currentTimeMillis()));
//        refund.setRefundTime(new Timestamp(System.currentTimeMillis()));
//        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
//        refund.setStatus(false);
//        refundRepository.save(refund);
        assertTrue(service.checkRefundStatus(refund));
    }

    @Test
    void acceptRefund() {
        String url = "http://localhost:8081/transfer";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", facility.getContact())
                .queryParam("receiver", customer.getContact())
                .queryParam("amount", 100);
        when(restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class))
                .thenReturn(null);
        when(refundRepository.findById("R01")).thenReturn(java.util.Optional.ofNullable(refund));
        when(refundRepository.save(refund)).thenReturn(refund);
        assertEquals(refund, service.acceptRefund(refund.getId(), BigDecimal.valueOf(100)));
    }

    @Test
    void getById() {
        refundRepository.save(refund);
        when(refundRepository.findById("R01")).thenReturn(java.util.Optional.ofNullable(refund));
        Refund returned = service.getById("R01");
//        verify(service).getById("R01");
        assertNotNull(returned);

    }

    @Test
    void deleteById() {
        refund.setId("R01");
        refund.setBook(book);
        refund.setRequestRefundTime(new Timestamp(System.currentTimeMillis()));
        refund.setRefundTime(new Timestamp(System.currentTimeMillis()));
        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
        refund.setStatus(false);
        refundRepository.save(refund);
        when(refundRepository.findById("R01")).thenReturn(java.util.Optional.ofNullable(refund));
        doNothing().when(refundRepository).deleteById("R01");
        service.deleteById("R01");
        assertEquals(0, refundRepository.findAll().size());
    }

    @Test
    void getAllRefund() {

    }
}