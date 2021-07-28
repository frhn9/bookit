package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.FeedbackDTO;
import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.FeedbackRepository;
import com.enigma.bookit.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedbackServiceImplTest {

    @Autowired
    FeedbackServiceImpl feedbackService;

    @MockBean
    FeedbackRepository feedbackRepository;


    private Feedback feedback;
    private ModelMapper modelMapper = new ModelMapper();

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

    @Test
    void getAll(){
        feedback = new Feedback("FB01", new Book(), 5, "Feedback", "Response");
        FeedbackSearchDTO feedbackSearchDTO = new FeedbackSearchDTO();
        feedbackSearchDTO.setRatingMore(0);

        FeedbackDTO feedbackDTO = feedbackService.convertFeedbackToFeedbackDTO(feedback);
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
        Page<Feedback> feedbackPage = new Page<Feedback>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Feedback, ? extends U> function) {
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
            public List<Feedback> getContent() {
                return null;
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
            public Iterator<Feedback> iterator() {
                return null;
            }
        };
        when(feedbackRepository.findAll((Specification<Feedback>) any(), any())).thenReturn(feedbackPage);
        when(feedbackService.getAllFeedback(any(),eq(feedbackSearchDTO))).thenReturn(feedbackDTOPage);
        assertEquals(1, feedbackDTOPage.getContent().size());

    }

    @Test
    void validatePresent(){
        when(feedbackRepository.findById("asal")).thenThrow(DataNotFoundException.class);

        doThrow(DataNotFoundException.class).when(mock(feedbackService.getClass())).validatePresent("asal");

    }

}