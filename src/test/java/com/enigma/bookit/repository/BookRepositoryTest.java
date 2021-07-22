package com.enigma.bookit.repository;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.entity.Payment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityManager entityManager;

    private Book book;

    @BeforeEach
    void setup(){
        book = new Book();
        book.setId("c1");
        book.setActiveFrom(Timestamp.valueOf("2021-07-12 10:00:00"));
        book.setActiveUntil(Timestamp.valueOf("2021-07-12 11:00:00"));
        book.setPayment(book.getPayment());
    }
    @Test
    void shouldSave_Category_GetId() {
        Book input = bookRepository.save(book);
        assertNotNull(entityManager.find(Book.class, input.getId()));

    }
    @Test
    void shouldFindAll_Category_GetId() {
        Book input = bookRepository.save(book);
        List<Book> books = new ArrayList<>();
        books.add(input);
        assertNotNull(books);
    }

    @AfterEach
    void deleteAll(){
        bookRepository.deleteAll();
    }

}
