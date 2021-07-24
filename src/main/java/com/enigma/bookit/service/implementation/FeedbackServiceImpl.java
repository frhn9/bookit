package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.FeedbackRepository;
import com.enigma.bookit.service.FeedbackService;
import com.enigma.bookit.specification.FeedbackSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    //Customer send a feedback of a facility
    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    //Owner respond customer's feedback
    public Feedback respondFeedback (String id, String response){
        Feedback feedback = getById(id);
        feedback.setResponse(response);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Feedback getById(String id) {
        validatePresent(id);
        return feedbackRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        validatePresent(id);
        feedbackRepository.deleteById(id);
    }

    @Override
    public Page<Feedback> getAllFeedback(Pageable pageable, FeedbackSearchDTO feedbackSearchDTO) {
        Specification<Feedback> feedbackSpecification = FeedbackSpecification.getSpecification(feedbackSearchDTO);
        return feedbackRepository.findAll(feedbackSpecification, pageable);
    }

    public void validatePresent(String id){
        if(!feedbackRepository.findById(id).isPresent()){
            String message = "id not found";
            throw new DataNotFoundException(message);
        }
    }
}
