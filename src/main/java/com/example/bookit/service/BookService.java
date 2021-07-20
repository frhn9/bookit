package com.example.bookit.service;

import com.example.bookit.entity.Book;
import com.example.bookit.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
        public Book addBook (Book book);
        public Book getBookById(String id);
        public List<Book> getAllBook();
        public void deleteBook(String id);

        void updateBook(String id, Book book);

        public Page<Book> getBookPerPage(Pageable pageable);
}
