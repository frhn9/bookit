package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.externalId", Matchers.is(refund.getId())));
    }

    @Test
    void searchRefundPerPage() throws Exception {
        refund = new Refund();
        refund.setId("R01");
        Book book = new Book();
        book.setId("B01");
        refund.setBook(book);
        refund.setRefundAmount(BigDecimal.valueOf(1000));

        RefundSearchDTO refundSearchDTO = new RefundSearchDTO();
        refundSearchDTO.setAmountMore(BigDecimal.valueOf(0));

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setId(refund.getId());
        refundDTO.setRefundAmount(refund.getRefundAmount());
        refundDTO.setBook(refund.getBook());

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

        when(refundService.getAllRefund(any(), any())).thenReturn(refundDTOPage);
        mockMvc.perform(get("/api/refund")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(refundSearchDTO)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data[0].id",is("R01")));

    }
}