package com.enigma.bookit.dto;

import com.enigma.bookit.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter @Setter @NoArgsConstructor
public class FeedbackDTO {
    private String id;
    private Book book;
    private Integer rating;
    private String feedback;
    private String response;
}
