package com.enigma.bookit.service.implementation;

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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
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

    @Override
    //harus cek jam ini udah dipesan atau belum
    public Payment save(Payment payment) {

        String facilityId = payment.getFacility().getId();
        Integer limit = facilityService.getFacilityById(facilityId).getCapacity();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp due = Timestamp.valueOf(formatter.format((new Timestamp(System.currentTimeMillis() + 3600000))));
        payment.setDueTime(due);
        //get facility
        Facility facility = facilityService.getFacilityById(facilityId);
        switch(payment.getPackageChosen()){
            //once rent, the price determined by the duration
            case ONCE:
                BigDecimal price = facility.getRentPriceOnce();
                //get duration
                BigDecimal duration = timeDiffer(payment.getBookingStart(), payment.getBookingEnd());
                BigDecimal amount = duration.multiply(price);
                payment.setAmount(amount);
                break;
            case WEEKLY:
                payment.setAmount(facility.getRentPriceWeekly());
                payment.setBookingEnd(new Timestamp(payment.getBookingStart().getTime() + TimeUnit.DAYS.toMillis(7)));
//                Timestamp weekTime = Timestamp.valueOf(formatter.format(String.valueOf(payment.getBookingStart().getTime() + TimeUnit.DAYS.toMillis(7))));
                break;
            case MONTHLY:
                payment.setAmount(facility.getRentPriceMonthly());
                payment.setBookingEnd(new Timestamp(payment.getBookingStart().getTime() + TimeUnit.DAYS.toMillis(30)));
                break;
        }

        //Check if the facility hasn't reached the max capacity
        List<Integer> curCap = bookService.getCapacity(facilityId, payment.getBookingStart(), payment.getBookingEnd());
        System.out.println(curCap);

        Integer cap = 0;
        if(curCap.size() == 0){
            cap = 0;
        }else{
            cap = curCap.get(0);
        }

        if(limit <= cap){
            throw new BadRequestException("Cannot add payment");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getById(String id) {
        validatePresent(id);
        return paymentRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        validatePresent(id);
        paymentRepository.deleteById(id);
    }

    @Override
    //harus cek jam ini udah dipesan atau belum dari tabel book
    public Payment pay(String id) {
        Payment payment = getById(id);

        if(payment.isPaymentStatus()){
            throw new BadRequestException("The payment has been already paid");
        }

        payment.setPaymentStatus(true);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp payDate = Timestamp.valueOf(formatter.format((new Timestamp(System.currentTimeMillis()))));
        payment.setPaymentDate(payDate);
        Book book = new Book();
        book.setPayment(payment);
        book.setActiveFrom(payment.getBookingStart());
        book.setActiveUntil(payment.getBookingEnd());
        bookService.addBook(book);

        String facilityContact = facilityService.getFacilityById(payment.getFacility().getId()).getContact();
        String customerContact = customerService.getById(payment.getCustomer().getId()).getContact();

        String url = "http://localhost:8081/transfer";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sender", customerContact)
                .queryParam("receiver", facilityContact)
                .queryParam("amount", payment.getAmount());
        // http://localhost:8081/debit?phoneNumber=082100000&amount=9000

        restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);
        return paymentRepository.save(payment);
    }

    @Override
    public Page<Payment> getAllPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO) {
        Specification<Payment> paymentSpecification = PaymentSpecification.getSpecification(paymentSearchDTO);
        return paymentRepository.findAll(paymentSpecification, pageable);
    }

    private void validatePresent(String id){
        if(!paymentRepository.findById(id).isPresent()){
            String message = "id not found";
            throw new DataNotFoundException(message);
        }
    }

    private BigDecimal timeDiffer(Timestamp timeStart, Timestamp timeStop){
        BigDecimal difference = BigDecimal.valueOf(timeStop.getTime() - timeStart.getTime());
        return (difference.divide(BigDecimal.valueOf(1000 * 3600)));
    }
}
