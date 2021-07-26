package com.enigma.bookit.service;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface BookService {
        public Book addBook (Book book);
        public Book getBookById(String id);
        public List<Book> getAllBook();
        public void deleteBook(String id);

        void updateBook(String id, Book book);

        public Page<Book> getBookPerPage(Pageable pageable);

        public List<Integer> getCapacity(String id, Timestamp start, Timestamp stop);

        public void extendBook(String bookId, PackageChosen packageChosen);
}
