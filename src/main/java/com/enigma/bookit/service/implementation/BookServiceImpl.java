package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.BookSearchDto;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.repository.BookRepository;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Book deleteBook(String id) {
       bookRepository.deleteById(id);
        return null;
    }

    @Override
    public void updateBook(String id, Book book) {
        book = bookRepository.findById(id).get();
        book.setId(id);
            bookRepository.save(book);
    }
    @Override
    public List<Integer> getCapacity(String id, LocalDateTime start, LocalDateTime stop) {
        return bookRepository.countCap(id, start, stop);
    }

    @Override
    public Page<Book> searchBookPerPage(Pageable pageable, BookSearchDto bookSearchDto) {
        Specification<Book> bookSpecification = BookSpecification.getSpesification(bookSearchDto);
        return bookRepository.findAll(bookSpecification, pageable);
    }

    @Override
    public Boolean checkActiveBook(String id) {
            Book book = bookRepository.findById(id).get();
            if(LocalDateTime.now().isAfter(book.getActiveUntil())){
                return false;
            }
            return true;
        }
}

