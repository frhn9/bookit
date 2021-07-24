package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.repository.BookRepository;
import com.enigma.bookit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id).get();
    }

    public Boolean checkActiveBook(String id){
        Book book = bookRepository.findById(id).get();
        if(new Timestamp(new Date().getTime()).compareTo(book.getActiveUntil()) > 0){
            return false;
        }
        return true;
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);

    }

    @Override
    public void updateBook(String id, Book book) {
        if (getBookById(id) != null){
            book.setId(id);
            bookRepository.save(book);
    }
}
    @Override
    public Page<Book> getBookPerPage(Pageable pagable) {
        return bookRepository.findAll(pagable);
    }

    @Override
    public List<Integer> getCapacity(String id, Timestamp start, Timestamp stop) {
        return bookRepository.countCap(id, start, stop);
    }


}
