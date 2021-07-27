package com.enigma.bookit.service;

import com.enigma.bookit.dto.FeedbackDTO;
import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
    FeedbackDTO save(Feedback feedback);
    FeedbackDTO respondFeedback (String id, String response);
    FeedbackDTO getById(String id);
    void deleteById(String id);
    Page<FeedbackDTO> getAllFeedback(Pageable pageable, FeedbackSearchDTO feedbackSearchDTO);
}
