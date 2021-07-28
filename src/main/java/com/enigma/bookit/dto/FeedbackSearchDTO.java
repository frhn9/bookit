package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.service.BookService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter @Setter @NoArgsConstructor
public class FeedbackSearchDTO {

    private Book book;
    private Integer ratingMore;
    private Integer ratingLess;
    private String feedback;
    private String response;
    private String facilityName;
    private String customerName;
}
