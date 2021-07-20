package com.enigma.bookit.service;

import com.enigma.bookit.entity.Feedback;

public interface FeedbackService {
    public Feedback save(Feedback feedback);
    public Feedback respondFeedback (String id, String response);
    public Feedback getById(String id);
    public void deleteById(String id);
}
