package com.enigma.bookit.controller;

import com.enigma.bookit.entity.Feedback;
import com.enigma.bookit.service.FeedbackService;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    //Customer send feedback
    @PostMapping()
    public ResponseEntity<Response<Feedback>> createFeedback(@RequestBody Feedback feedback){
        Response<Feedback> response = new Response<>();
        String message = "Feedback is inserted";
        response.setMessage(message);
        response.setData(feedbackService.save(feedback));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    //Owner response to customer's feedback
    @PutMapping("/{id}")
    public ResponseEntity<Response<Feedback>> responseFeedback(@PathVariable String id,@RequestBody String responseMsg){
        Response<Feedback> response = new Response<>();
        String message = "a response was sent";
        response.setMessage(message);
        response.setData(feedbackService.respondFeedback(id, responseMsg));
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
}
