package com.enigma.bookit.controller;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.service.CategoryService;
import com.enigma.bookit.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

        @Autowired
        BookService bookService;


        @GetMapping("/{bookId}")
        public Book getBookById(@PathVariable String bookId){
            return bookService.getBookById(bookId);
        }

        @GetMapping
        public List<Book> getAllBook(){
            return bookService.getAllBook();
        }

        @DeleteMapping("/{bookId}")
        public void deleteBook(@PathVariable("bookId") String bookId){
            bookService.deleteBook(bookId);
        }
}

