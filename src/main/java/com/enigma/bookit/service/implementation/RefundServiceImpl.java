package com.enigma.bookit.service.implementation;

import com.enigma.bookit.constant.ErrorMessageConstant;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.dto.RefundDTO;
import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.exception.BadRequestException;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.RefundRepository;
import com.enigma.bookit.service.*;
import com.enigma.bookit.specification.RefundSpecification;
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
import java.time.LocalDateTime;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @Autowired
    FacilityService facilityService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

    //Customer apply for refund
    @Override
    public RefundDTO applyRefund(Refund refund) {
        if(bookService.checkActiveBook(refund.getBook().getId())) {
//        if(checkActiveBook(refund.getBook().getId())){
            refund.setRequestRefundTime(LocalDateTime.now());
            refund.setStatus(false);
            return convertRefundToRefundDTO(refundRepository.save(refund));
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
    public RefundDTO acceptRefund(String id, BigDecimal amount) {
        Refund refund = refundRepository.findById(id).get();
        if(checkRefundStatus(refund)) {
            refund.setStatus(true);
            refund.setRefundTime(LocalDateTime.now());
            refund.setRefundAmount(amount);
            Book book = bookService.getBookById(refund.getBook().getId());
            book.setActiveUntil(LocalDateTime.now());
            PaymentDTO payment = paymentService.getById(book.getPayment().getId());
            bookService.addBook(book);
            return convertRefundToRefundDTO(refundRepository.save(refund));
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
    public RefundDTO getById(String id) {
        if(refundRepository.findById(id).isPresent()) {
            return convertRefundToRefundDTO(refundRepository.findById(id).get());
        }
        throw new DataNotFoundException(String.format(ErrorMessageConstant.DATA_NOT_FOUND, "id "));
    }

    @Override
    public void deleteById(String id) {
        if(refundRepository.findById(id).isPresent()) {
            refundRepository.deleteById(id);
        }throw new DataNotFoundException(String.format(ErrorMessageConstant.DATA_NOT_FOUND, "id"));
    }

    @Override
    public Page<RefundDTO> getAllRefund(Pageable pageable, RefundSearchDTO refundSearchDTO) {
        Specification<Refund> refundSpecification = RefundSpecification.getSpecification(refundSearchDTO);
        Page<Refund> result = refundRepository.findAll(refundSpecification, pageable);
        return result.map(this::convertRefundToRefundDTO);
    }

    public RefundDTO convertRefundToRefundDTO(Refund refund){
        return modelMapper.map(refund, RefundDTO.class);
    }

    public Refund convertRefundDTOToRefund(RefundDTO refundDTO){
        return modelMapper.map(refundDTO, Refund.class);
    }
}
