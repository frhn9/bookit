package com.enigma.bookit.controller;

import com.enigma.bookit.dto.FeedbackDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

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
}