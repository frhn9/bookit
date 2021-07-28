package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Page<Feedback> findAll(Specification<Feedback> feedbackSpecification, Pageable pageable);
}
