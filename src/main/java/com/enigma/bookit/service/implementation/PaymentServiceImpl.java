package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ResponseMessage;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.exception.BadRequestException;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.PaymentRepository;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.service.CustomerService;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.service.PaymentService;
import com.enigma.bookit.specification.PaymentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    FacilityService facilityService;
    @Autowired
    BookService bookService;
    @Autowired
    CustomerService customerService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ModelMapper modelMapper;

    @Override
    //harus cek jam ini udah dipesan atau belum
    public PaymentDTO save(Payment payment) {

        String facilityId = payment.getFacility().getId();
        Integer limit = facilityService.getFacilityById(facilityId).getCapacity();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime due = LocalDateTime.now().plusHours(2);
        payment.setDueTime(due);
        //get facility
        Facility facility = facilityService.getFacilityById(facilityId);
        switch(payment.getPackageChosen()){
            //once rent, the price determined by the duration
            case ONCE:
                BigDecimal price = facility.getRentPriceOnce();
                //get duration
                BigDecimal duration = BigDecimal.valueOf(ChronoUnit.HOURS.between(payment.getBookingStart(), payment.getBookingEnd()));
                BigDecimal amount = duration.multiply(price);
                payment.setAmount(amount);
                break;
            case WEEKLY:
                payment.setAmount(facility.getRentPriceWeekly());
                payment.setBookingEnd(payment.getBookingStart().plusDays(7));
//                Timestamp weekTime = Timestamp.valueOf(formatter.format(String.valueOf(payment.getBookingStart().getTime() + TimeUnit.DAYS.toMillis(7))));
                break;
            case MONTHLY:
                payment.setAmount(facility.getRentPriceMonthly());
                payment.setBookingEnd(payment.getBookingStart().plusDays(30));
                break;
        }

        //Check if the facility hasn't reached the max capacity
        checkFacilityCapacity(payment);
        paymentRepository.save(payment);
        return convertPaymentToPaymentDTO(payment);
    }

    @Override
    public PaymentDTO getById(String id) {
        validatePresent(id);
        return convertPaymentToPaymentDTO(paymentRepository.findById(id).get());
    }

    @Override
    public void deleteById(String id) {
        validatePresent(id);
        paymentRepository.deleteById(id);
    }

    @Override
    //harus cek jam ini udah dipesan atau belum dari tabel book
    public PaymentDTO pay(String id) {
        Payment payment = paymentRepository.findById(id).get();
        checkFacilityCapacity(payment);

        if(payment.isPaymentStatus()){
            throw new BadRequestException("The payment has been already paid");
        }

        if(payment.getDueTime().isBefore(LocalDateTime.now())){
            throw new BadRequestException("Transaction already closed, please create new transaction");
        }

        payment.setPaymentStatus(true);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        payment.setPaymentDate(LocalDateTime.now());
        Book book = new Book();
        book.setPayment(payment);
        book.setActiveFrom(payment.getBookingStart());
        book.setActiveUntil(payment.getBookingEnd());
        bookService.addBook(book);

        String facilityContact = facilityService.getFacilityById(payment.getFacility().getId()).getContact();
        String customerContact = customerService.getById(payment.getCustomer().getId()).getContact();



//        String url = "http://localhost:8081/transfer";
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("sender", customerContact)
//                .queryParam("receiver", facilityContact)
//                .queryParam("amount", payment.getAmount());
//        // http://localhost:8081/debit?phoneNumber=082100000&amount=9000
//
//        restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);
        paymentRepository.save(payment);
        return convertPaymentToPaymentDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO) {
        Specification<Payment> paymentSpecification = PaymentSpecification.getSpecification(paymentSearchDTO);
        return paymentRepository.findAll(paymentSpecification, pageable);
    }

    public void validatePresent(String id){
        if(!paymentRepository.findById(id).isPresent()){
            throw new DataNotFoundException(ResponseMessage.NOT_FOUND);
        }
    }

    public void checkFacilityCapacity(Payment payment){
        Facility facility = facilityService.getFacilityById(payment.getFacility().getId());
        List<Integer> curCap = bookService.getCapacity(facility.getId(), payment.getBookingStart(), payment.getBookingEnd());
        Integer cap = 0;
        if(curCap.size() == 0){
            cap = 0;
        }else{
            cap = curCap.get(0);
        }

        if(facility.getCapacity() <= cap){
            throw new BadRequestException("Maximum facility's capacity has been reached");
        }
    }
    public PaymentDTO convertPaymentToPaymentDTO(Payment payment) {
        return modelMapper.map(payment, PaymentDTO.class);
    }
}
