package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.dto.CallbackDTO;
import com.enigma.bookit.dto.InvoiceResponseDTO;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.dto.PaymentSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.exception.BadRequestException;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.PaymentRepository;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.service.PaymentService;
import com.enigma.bookit.service.UserService;
import com.enigma.bookit.specification.PaymentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    FacilityService facilityService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ModelMapper modelMapper;

    @Override
    //harus cek jam ini udah dipesan atau belum
    public PaymentDTO save(Payment payment) {

        String facilityId = payment.getFacility().getId();
        Integer limit = facilityService.getFacilityById(facilityId).getCapacity();
        Facility facility = facilityService.getFacilityById(facilityId);
        switch(payment.getPackageChosen()){
            case ONCE:
                BigDecimal price = facility.getRentPriceOnce();
                //get duration
                BigDecimal duration = BigDecimal.valueOf(ChronoUnit.HOURS.between(payment.getBookingStart(), payment.getBookingEnd()));
                BigDecimal amount = duration.multiply(price);
                payment.setPaidAmount(amount);
                break;
            case WEEKLY:
                payment.setPaidAmount(facility.getRentPriceWeekly());
                payment.setBookingEnd(payment.getBookingStart().plusDays(7));
                break;
            case MONTHLY:
                payment.setPaidAmount(facility.getRentPriceMonthly());
                payment.setBookingEnd(payment.getBookingStart().plusDays(30));
                break;
        }

        //Check if the facility hasn't reached the max capacity
        payment.setPaymentStatus("PENDING");
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
    public PaymentDTO pay(CallbackDTO callbackDTO) {
        Payment payment = paymentRepository.findById(callbackDTO.getExternal_id()).get();
        checkFacilityCapacity(payment);
        payment.setPaymentStatus(callbackDTO.getStatus());
        LocalDateTime dateTime = LocalDateTime.now();
        payment.setPaymentDate(dateTime);

        payment.setPaymentDate(LocalDateTime.now());
        Book book = new Book();
        book.setPayment(payment);
        book.setActiveFrom(payment.getBookingStart());
        book.setActiveUntil(payment.getBookingEnd());
        bookService.addBook(book);


        paymentRepository.save(payment);
        return convertPaymentToPaymentDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO) {
        Specification<Payment> paymentSpecification = PaymentSpecification.getSpecification(paymentSearchDTO);
        Page<Payment> payments = paymentRepository.findAll(paymentSpecification, pageable);
        return payments.map(this::convertPaymentToPaymentDTO);
    }

    public void validatePresent(String id){
        if(!paymentRepository.findById(id).isPresent()){
            throw new DataNotFoundException(String.format(ErrorMessageConstant.DATA_NOT_FOUND, "id"));
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

    @Override
    public PaymentDTO extendBook(String bookId, PackageChosen packageChosen) {
        Book book = bookService.getBookById(bookId);
        Payment payment = paymentRepository.findById(book.getPayment().getId()).get();
        Payment newPayment = new Payment();
        newPayment.setUser(payment.getUser());
        newPayment.setFacility(payment.getFacility());
        newPayment.setPackageChosen(packageChosen);
        newPayment.setBookingStart(book.getActiveUntil());
        newPayment.setPaymentStatus("PENDING");
        return save(newPayment);
    }

    public PaymentDTO convertPaymentToPaymentDTO(Payment payment) {
        return modelMapper.map(payment, PaymentDTO.class);
    }
}
