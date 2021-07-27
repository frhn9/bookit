package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.RefundDTO;
import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.service.RefundService;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(ApiUrlConstant.REFUND)
public class RefundController {

    @Autowired
    RefundService refundService;

    //customer apply a refund of an active book
    @PostMapping
    public ResponseEntity<Response<RefundDTO>> applyRefund(@RequestBody Refund refund){
        Response <RefundDTO> response = new Response<>();
        String message = String.format(SuccessMessageConstant.CREATE_SUCCESS,"refund's");
        response.setMessage(message);
        response.setData(refundService.applyRefund(refund));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    //facility pay the refound to the customer
    @PutMapping("/{id}")
    public ResponseEntity<Response<RefundDTO>> acceptRefund (@PathVariable String id,
                                                          @RequestBody Refund refund) {
        Response<RefundDTO> response = new Response<>();
        String message = String.format(SuccessMessageConstant.CREATE_SUCCESS, "refund's");
        response.setMessage(message);
        response.setData(refundService.acceptRefund(id, refund.getRefundAmount()));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public PageResponseWrapper<RefundDTO> searchFeedbackPerPage(@RequestBody RefundSearchDTO refundSearchDTO,
                                                             @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                             @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                                             @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                             @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        Page<RefundDTO> refundPage = refundService.getAllRefund(pageable, refundSearchDTO);
        return new PageResponseWrapper<RefundDTO>(code, status, message,refundPage);
    }
}
