package com.enigma.bookit.controller;

import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.FeedbackDTO;
import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.service.FeedbackService;
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
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    @MockBean
    FeedbackService feedbackService;

    @Autowired
    FeedbackController feedbackController;

    @Autowired
    MockMvc mockMvc;


    private ModelMapper modelMapper = new ModelMapper();
    private Feedback feedback;

    @BeforeEach
    void setup(){
        feedback = new Feedback();
        feedback.setId("f01");
        Book book = new Book();
        book.setId("B01");
        feedback.setBook(book);
        feedback.setFeedback("Feedback");
        feedback.setResponse("Response");
    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void createFeedback() throws Exception {
        when(feedbackService.save(any(Feedback.class)))
                .thenReturn(modelMapper.map(feedback, FeedbackDTO.class));

        mockMvc.perform(post("/api/feedback")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(feedback)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", Matchers.is(feedback.getId())));

    }

    @Test
    void responseFeedback() throws Exception {
        when(feedbackService.respondFeedback(any(String.class) ,any(String.class)))
                .thenReturn(modelMapper.map(feedback, FeedbackDTO.class));

        mockMvc.perform(put("/api/feedback/{id}", feedback.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(feedback)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", Matchers.is(feedback.getId())));
    }

    @Test
    void deleteFeedback() throws Exception {
        String feedbackId = feedback.getId();
        doNothing().when(feedbackService).deleteById(feedbackId);

        mockMvc.perform(delete("/api/feedback/{id}", feedbackId))
                .andExpect(status().isOk());
    }

    @Test
    void getAllFeedback_shouldSendOkResponse() throws Exception {
        feedback = new Feedback();
        feedback.setId("f01");
        Book book = new Book();
        book.setId("B01");
        feedback.setBook(book);
        feedback.setRating(5);
        feedback.setFeedback("Feedback");
        feedback.setResponse("Response");

        FeedbackSearchDTO feedbackSearchDTO = new FeedbackSearchDTO();
        feedbackSearchDTO.setRatingMore(0);

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId("f01");
        feedbackDTO.setRating(5);

        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        feedbackDTOS.add(feedbackDTO);

        Page<FeedbackDTO> feedbackDTOPage = new Page<FeedbackDTO>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super FeedbackDTO, ? extends U> function) {
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
            public List<FeedbackDTO> getContent() {
                return feedbackDTOS;
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
            public Iterator<FeedbackDTO> iterator() {
                return null;
            }
        };

        when(feedbackService.getAllFeedback(any(), any())).thenReturn(feedbackDTOPage);
        mockMvc.perform(get("/api/feedback")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(feedbackSearchDTO)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)));
    }
}