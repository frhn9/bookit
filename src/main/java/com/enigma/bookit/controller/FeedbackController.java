package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.FeedbackDTO;
import com.enigma.bookit.dto.FeedbackSearchDTO;
import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.service.FeedbackService;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrlConstant.FEEDBACK)
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    //Customer send feedback
    @PostMapping()
    public ResponseEntity<Response<FeedbackDTO>> createFeedback(@RequestBody Feedback feedback){
        Response<FeedbackDTO> response = new Response<>();
        String message = String.format(SuccessMessageConstant.CREATE_SUCCESS, "feedback");
        response.setMessage(message);
        response.setData(feedbackService.save(feedback));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    //Owner response to customer's feedback
    @PutMapping("/{id}")
    public ResponseEntity<Response<FeedbackDTO>> responseFeedback(@PathVariable String id, @RequestBody Feedback feedback){
        Response<FeedbackDTO> response = new Response<>();
        String message = String.format(SuccessMessageConstant.CREATE_SUCCESS, "response of a feedback");
        response.setMessage(message);
        response.setData(feedbackService.respondFeedback(id, feedback.getResponse()));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    //Owner delete inappropriate feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Feedback>> deleteFeedback(@PathVariable String id) {
        Response<Feedback> response = new Response<>();
        String message = "a response was deleted";
        response.setMessage(message);
        feedbackService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    //Get All Response per Page
    @GetMapping
    public PageResponseWrapper<FeedbackDTO> searchFeedbackPerPage(@RequestBody FeedbackSearchDTO feedbackSearchDTO,
                                                               @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                                               @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                               @RequestParam(name = "direction", defaultValue = "ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        Page<FeedbackDTO> feedbackPage = feedbackService.getAllFeedback(pageable, feedbackSearchDTO);
        return new PageResponseWrapper<FeedbackDTO>(code, status, message, feedbackPage);
    }
}
