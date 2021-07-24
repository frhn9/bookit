package com.enigma.bookit.service;

import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {
    public Feedback save(Feedback feedback);
    public Feedback respondFeedback (String id, String response);
    public Feedback getById(String id);
    public void deleteById(String id);
    public Page<Feedback> getAllFeedback(Pageable pageable, FeedbackSearchDTO feedbackSearchDTO);
}
