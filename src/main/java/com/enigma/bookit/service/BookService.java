package com.enigma.bookit.service;

import com.enigma.bookit.dto.BookSearchDto;
import com.enigma.bookit.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface BookService {
        public Book addBook (Book book);
        public Book getBookById(String id);
        List<Book> getAllBook();
        Book deleteBook(String id);
        void updateBook(String id, Book book);
        public List<Integer> getCapacity (String id, LocalDateTime start, LocalDateTime stop);
        public Page<Book> searchBookPerPage(Pageable pageable, BookSearchDto bookSearchDto);
        public Boolean checkActiveBook(String id);
}
