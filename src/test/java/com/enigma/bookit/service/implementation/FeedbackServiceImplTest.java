package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @InjectMocks
    FeedbackServiceImpl feedbackService;

    @Mock
    FeedbackRepository feedbackRepository;

    @Autowired
    MockMvc mockMvc;

    private Feedback feedback;

    @Test
    void save() {
        feedback = new Feedback("FB01", new Book(), 5, "Feedback", "Response");
        when(feedbackRepository.save(feedback)).thenReturn(feedback);
        assertEquals(feedback.getId(), feedbackService.save(feedback).getId());
    }

    @Test
    void respondFeedback() {
        feedback = new Feedback("FB01", new Book(), 5, "Feedback", "Response");
        feedbackRepository.save(feedback);
        String id = feedback.getId();
        String newResponse = "newResponse";
        feedback.setResponse(newResponse);
        when(feedbackRepository.findById("FB01")).thenReturn(java.util.Optional.ofNullable(feedback));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        assertSame(newResponse, feedbackService.respondFeedback(id, newResponse).getResponse());
    }

    @Test
    void deleteById() {
        feedback = new Feedback("FB01", new Book(), 5, "Feedback", "Response");
        feedbackRepository.save(feedback);
        String id = feedback.getId();
        when(feedbackRepository.findById("FB01")).thenReturn(java.util.Optional.ofNullable(feedback));
        feedbackService.deleteById(id);
        assertEquals(0, feedbackRepository.findAll().size());
    }
}