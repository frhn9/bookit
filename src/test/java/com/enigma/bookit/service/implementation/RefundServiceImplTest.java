package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.RefundDTO;
import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.*;
import com.enigma.bookit.entity.user.User;
import com.enigma.bookit.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RefundServiceImplTest {

    @Autowired
    RefundServiceImpl service;

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
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    private Refund refund = new Refund();
    private Book book =  new Book();
    private Payment payment = new Payment();
    private User user = new User();
    private Facility facility = new Facility();

    @BeforeEach
    void setup(){
        user.setId("C01");
        user.setContact("0812345");
        userRepository.save(user);
        when(userRepository.findById("C01")).thenReturn(java.util.Optional.ofNullable(user));

        facility.setId("F01");
        facility.setContact("0854321");
        facilityRepository.save(facility);
        when(facilityRepository.findById("F01")).thenReturn(java.util.Optional.ofNullable(facility));

        payment.setId("P01");
        payment.setPaymentStatus("PENDING");
        payment.setPackageChosen(PackageChosen.WEEKLY);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUser(user);
        payment.setFacility(facility);
        payment.setBookingStart(LocalDateTime.now());
        payment.setBookingEnd(LocalDateTime.now().plusDays(7));
        payment.setDueTime(LocalDateTime.now().plusHours(2));
        paymentRepository.save(payment);
        when(paymentRepository.findById("P01")).thenReturn(java.util.Optional.ofNullable(payment));

        book.setId("B01");
        book.setActiveUntil(LocalDateTime.now().plusDays(7));
        book.setActiveFrom(LocalDateTime.now());
        book.setPayment(payment);
        bookRepository.save(book);
        when(bookRepository.findById("B01")).thenReturn(java.util.Optional.of(book));

        refund.setId("R01");
        refund.setBook(book);
        refund.setRequestRefundTime(LocalDateTime.now());
        refund.setRefundTime(LocalDateTime.now());
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
        assertEquals(refund.getId(), service.applyRefund(refund).getId());
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

//    @Test
//    void acceptRefund() {
//        String url = "http://localhost:8081/transfer";
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("sender", facility.getContact())
//                .queryParam("receiver", customer.getContact())
//                .queryParam("amount", 100);
//        when(restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class))
//                .thenReturn(null);
//        when(refundRepository.findById("R01")).thenReturn(java.util.Optional.ofNullable(refund));
//        when(refundRepository.save(refund)).thenReturn(refund);
//        assertEquals(refund.getId(), service.acceptRefund(refund.getId(), BigDecimal.valueOf(100)).getId());
//    }

    @Test
    void getById() {
        refund = new Refund();
        Refund output = new Refund();

        refund.setId("R01");
        refund.setBook(book);
        refund.setRequestRefundTime(LocalDateTime.now());
        refund.setRefundTime(LocalDateTime.now());
        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
        refund.setStatus(false);
        refundRepository.save(refund);

        when(refundRepository.findById(refund.getId())).thenReturn(java.util.Optional.ofNullable(refund));
        output.setId(service.getById("R01").getId());
        assertEquals(refund.getId(), output.getId());
    }

    @Test
    void deleteById() {
        refund = new Refund();
        Refund output = new Refund();

        refund.setId("R01");
        refund.setBook(book);
        refund.setRequestRefundTime(LocalDateTime.now());
        refund.setRefundTime(LocalDateTime.now());
        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
        refund.setStatus(false);
        refundRepository.save(refund);
        when(refundRepository.findById(refund.getId())).thenReturn(Optional.of(refund));
        service.deleteById(refund.getId());
        assertEquals(0, refundRepository.findAll().size());
    }

    @Test
    void getAllPerPage(){
        refund = new Refund();
        Refund output = new Refund();

        refund.setId("R01");
        refund.setBook(book);
        refund.setRequestRefundTime(LocalDateTime.now());
        refund.setRefundTime(LocalDateTime.now());
        refund.setRefundAmount(BigDecimal.valueOf(1000.00));
        refund.setStatus(false);

        RefundSearchDTO refundSearchDTO = new RefundSearchDTO();
        refundSearchDTO.setAmountMore(BigDecimal.valueOf(0));

        RefundDTO refundDTO = service.convertRefundToRefundDTO(refund);
        List<RefundDTO> refundDTOS = new ArrayList<>();
        refundDTOS.add(refundDTO);

        Page<RefundDTO> refundDTOPage = new Page<RefundDTO>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super RefundDTO, ? extends U> function) {
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
            public List<RefundDTO> getContent() {
                return refundDTOS;
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
            public Iterator<RefundDTO> iterator() {
                return null;
            }
        };
        Page<Refund> refundPage = new Page<Refund>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Refund, ? extends U> function) {
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
            public List<Refund> getContent() {
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
            public Iterator<Refund> iterator() {
                return null;
            }
        };
        when(refundRepository.findAll((Specification<Refund>) any(), any())).thenReturn(refundPage);
        refundRepository.save(refund);
        when(service.getAllRefund(any(), eq(refundSearchDTO))).thenReturn(refundDTOPage);
        assertEquals(1, refundDTOPage.getContent().size());
    }

    @Test
    void acceptRefund(){
        Payment payment = new Payment();
        payment.setId("P01");
        Book book = new Book();
        book.setId("B01");
        book.setPayment(payment);
        book.setActiveUntil(LocalDateTime.now().plusHours(2));
        Refund refund = new Refund();
        refund.setId("R01");
        refund.setBook(book);
        refund.setStatus(false);
        RefundDTO refundDto = new RefundDTO();
        refundDto.setId(refund.getId());
        refundDto.setBook(refund.getBook());
        when(bookRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(book));
        when(refundRepository.findById(any(String.class))).thenReturn(java.util.Optional.of(refund));
        when(paymentRepository.findById(any(String.class))).thenReturn(Optional.of(payment));
        when(refundRepository.save(any(Refund.class))).thenReturn(refund);
        RefundDTO output = service.acceptRefund(refund.getId(), payment.getPaidAmount());
        assertEquals(refundDto.getId(), output.getId());

    }
}