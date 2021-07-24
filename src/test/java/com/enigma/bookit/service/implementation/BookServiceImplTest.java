package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addBook() {

    }

    @Test
    void checkActive(){
        Book book = new Book();
        book.setId("B01");
        book.setActiveUntil(new Timestamp(new Date().getTime() + TimeUnit.DAYS.toMillis(8)));
        book.setActiveFrom(new Timestamp(new Date().getTime()));
        book.setPayment(new Payment());
        bookRepository.save(book);
        when(bookRepository.findById("B01")).thenReturn(java.util.Optional.of(book));
        assertTrue(bookService.checkActiveBook("B01"));
    }

    @Test
    void getBookById() {
        Book book = new Book();
        book.setId("B01");
//        book.setPayment(new Payment());
        bookRepository.save(book);
        when(bookRepository.findById("B01")).thenReturn(java.util.Optional.of(book));
        assertEquals(book.getId(), bookService.getBookById("B01"));
    }

    @Test
    void getAllBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }

}