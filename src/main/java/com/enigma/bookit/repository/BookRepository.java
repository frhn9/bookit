package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
        @Query("SELECT COUNT(f.id) FROM Book b INNER JOIN b.payment p INNER JOIN p.facility f WHERE f.id = :id " +
            "AND :start <= b.activeUntil AND :stop >= b.activeFrom GROUP BY f.id")
    List<Integer> countCap (@Param("id") String id, @Param("start") LocalDateTime start, @Param("stop")LocalDateTime stop);
}
