package com.enigma.bookit.repository;

import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.entity.Feedback;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FeedbackRepositoryTest {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    EntityManager entityManager;

    private Feedback feedback;
    @BeforeEach
    void setup(){
        feedback = new Feedback();
        feedback.setId("FB01");
        feedback.setBook(new Book());
        feedback.setFeedback("Feedback");
        feedback.setResponse("Response");
    }

    @Test
    void shouldSaveFeedback(){
        Feedback input = feedbackRepository.save(feedback);
        assertNotNull(entityManager.find(Feedback.class, input.getId()));
    }

//    @AfterEach
//    void deleteAll(){
//        feedbackRepository.deleteAll();
//    }
}