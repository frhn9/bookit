package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ResponseMessage;
import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.exception.BadRequestException;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.RefundRepository;
import com.enigma.bookit.service.*;
import com.enigma.bookit.specification.RefundSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    CustomerService customerService;

    @Autowired
    FacilityService facilityService;

    @Autowired
    RestTemplate restTemplate;

    //Customer apply for refund
    @Override
    public Refund applyRefund(Refund refund) {
        if(bookService.checkActiveBook(refund.getBook().getId())) {
//        if(checkActiveBook(refund.getBook().getId())){
            refund.setRequestRefundTime(new Timestamp(new Date().getTime()));
            refund.setStatus(false);
            return refundRepository.save(refund);
        }
        else{
            throw new BadRequestException("The book period has been already ended");
        }
    }

//    public void checkActiveBook(String id){
//        Book book = bookService.getBookById(id);
//        if(new Timestamp(new Date().getTime()).compareTo(book.getActiveUntil()) > 0){
//            throw new BadRequestException("The book period has been already ended");
//        }
//    }



    @Override
    public Refund acceptRefund(String id, BigDecimal amount) {
        Refund refund = refundRepository.findById(id).get();
        if(checkRefundStatus(refund)) {
            refund.setStatus(true);
            refund.setRefundTime(new Timestamp(new Date().getTime()));
            refund.setRefundAmount(amount);
            Book book = bookService.getBookById(refund.getBook().getId());
            book.setActiveUntil(new Timestamp(new Date().getTime()));
            Payment payment = paymentService.getById(book.getPayment().getId());
            String facilityContact = facilityService.getFacilityById(payment.getFacility().getId()).getContact();
            String customerContact = customerService.getById(payment.getCustomer().getId()).getContact();

            String url = "http://localhost:8081/transfer";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("sender", facilityContact)
                    .queryParam("receiver", customerContact)
                    .queryParam("amount", amount);
            // http://localhost:8081/debit?phoneNumber=082100000&amount=9000

            restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);
            bookService.addBook(book);
            return refundRepository.save(refund);
        }
        else{
            throw new BadRequestException("Refund has been paid already");
        }
    }

    public Boolean checkRefundStatus(Refund refund){
        if (!refund.getStatus()){
            return true;
        }return false;
    }

    @Override
    public Refund getById(String id) {
        if(refundRepository.findById(id).isPresent()) {
            return refundRepository.getById(id);
        }
        throw new DataNotFoundException(ResponseMessage.NOT_FOUND);
    }

    @Override
    public void deleteById(String id) {
        if (refundRepository.findById(id).isPresent()) {
            refundRepository.deleteById(id);
        }throw new DataNotFoundException(ResponseMessage.NOT_FOUND);
    }

    @Override
    public Page<Refund> getAllRefund(Pageable pageable, RefundSearchDTO refundSearchDTO) {
        Specification<Refund> refundSpecification = RefundSpecification.getSpecification(refundSearchDTO);
        return refundRepository.findAll(refundSpecification, pageable);
    }
}
