package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.BOOK)
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

