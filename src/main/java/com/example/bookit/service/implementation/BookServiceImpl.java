package com.example.bookit.service.implementation;

import com.example.bookit.entity.Book;
import com.example.bookit.repository.BookRepository;
import com.example.bookit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
