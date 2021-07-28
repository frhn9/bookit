package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.FeedbackDTO;
import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.dto.PaymentDTO;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.exception.DataNotFoundException;
import com.enigma.bookit.repository.FeedbackRepository;
import com.enigma.bookit.service.FeedbackService;
import com.enigma.bookit.specification.FeedbackSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    private ModelMapper modelMapper = new ModelMapper();

    //Customer send a feedback of a facility
    @Override
    public FeedbackDTO save(Feedback feedback) {
        return convertFeedbackToFeedbackDTO(feedbackRepository.save(feedback));
    }

    //Owner respond customer's feedback
    public FeedbackDTO respondFeedback (String id, String response){
        Feedback feedback = convertFeedbackDTOToFeedback(getById(id));
        feedback.setResponse(response);
        FeedbackDTO feedbackDTO = convertFeedbackToFeedbackDTO(feedbackRepository.save(feedback));
//        return convertFeedbackToFeedbackDTO(feedbackRepository.save(feedback));
        return feedbackDTO;
    }

    @Override
    public FeedbackDTO getById(String id) {
        validatePresent(id);
        return convertFeedbackToFeedbackDTO(feedbackRepository.findById(id).get());
    }

    @Override
    public void deleteById(String id) {
        validatePresent(id);
        feedbackRepository.deleteById(id);
    }

    @Override
    public Page<FeedbackDTO> getAllFeedback(Pageable pageable, FeedbackSearchDTO feedbackSearchDTO) {
        Specification<Feedback> feedbackSpecification = FeedbackSpecification.getSpecification(feedbackSearchDTO);
        Page<Feedback> result = feedbackRepository.findAll(feedbackSpecification, pageable);
        return result.map(this::convertFeedbackToFeedbackDTO);
    }

    public void validatePresent(String id){
        if(!feedbackRepository.findById(id).isPresent()){
            String message = "id not found";
            throw new DataNotFoundException(message);
        }
    }
    public FeedbackDTO convertFeedbackToFeedbackDTO(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    public Feedback convertFeedbackDTOToFeedback(FeedbackDTO feedbackDTO){
        return modelMapper.map(feedbackDTO, Feedback.class);
    }
}
