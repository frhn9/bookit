package com.enigma.bookit.controller;

import com.enigma.bookit.dto.RefundDTO;
import com.enigma.bookit.dto.RefundSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Refund;
import com.enigma.bookit.service.FeedbackService;
import com.enigma.bookit.service.RefundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RefundController.class)
class RefundControllerTest {

    @MockBean
    RefundService refundService;

    @Autowired
    MockMvc mockMvc;


    private Refund refund;
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup(){
        refund = new Refund();
        refund.setId("R01");
        Book book = new Book();
        book.setId("B01");
        refund.setBook(book);
        refund.setRefundAmount(BigDecimal.valueOf(1000));
//        refund.setRefundTime(LocalDateTime.now());
//        refund.setRequestRefundTime(LocalDateTime.now());
    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void applyRefund() throws Exception {
        when(refundService.applyRefund(any(Refund.class)))
                .thenReturn(modelMapper.map(refund, RefundDTO.class));

        mockMvc.perform(post("/api/refund")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(refund)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", Matchers.is(refund.getId())));
    }

    @Test
    void acceptRefund() throws Exception {
        when(refundService.acceptRefund(any(String.class), any(BigDecimal.class)))
                .thenReturn(modelMapper.map(refund, RefundDTO.class));

        mockMvc.perform(put("/api/refund/{id}", refund.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(refund)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.is(refund.getId())));
    }

    @Test
    void searchFeedbackPerPage() {

    }
}