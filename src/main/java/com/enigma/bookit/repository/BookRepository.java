package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Page<Book> findAll(Specification<Book> bookSpecification, Pageable pageable);

    @Query("SELECT COUNT(f.id) FROM Book b INNER JOIN b.payment p INNER JOIN p.facility f where f.id = :id " +
            "AND :start <= b.activeUntil AND :stop >= b.activeFrom GROUP BY f.id")
    List<Integer> countCap (@Param("id") String id, @Param("start") LocalDateTime start, @Param("stop")LocalDateTime stop);
}
