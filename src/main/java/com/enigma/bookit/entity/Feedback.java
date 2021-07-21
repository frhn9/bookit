package com.enigma.bookit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mst_feedback")
@Getter @Setter @NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "feedback_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("feedback")
    private Book book;
    private Integer rating;
    @Column(columnDefinition="TEXT")
    private String feedback;
    @Column(columnDefinition="TEXT")
    private String response;

    public Feedback(String id, Book book, Integer rating, String feedback) {
        this.id = id;
        this.book = book;
        this.rating = rating;
        this.feedback = feedback;
    }
}
