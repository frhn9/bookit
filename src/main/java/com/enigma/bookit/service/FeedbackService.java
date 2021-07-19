package com.enigma.bookit.service;

import com.enigma.bookit.entity.Feedback;

public interface FeedbackService {
    public Feedback saveFeedback(Feedback feedback);
    public Feedback respondFeedback (String id, String response);
    public Feedback getFeedbackById(String id);
    public void deleteFeedback(String id);
}
