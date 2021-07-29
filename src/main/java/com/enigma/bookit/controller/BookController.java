package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.BookSearchDto;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.BOOK)
public class BookController {

        @Autowired
        BookService bookService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @PostMapping()
    public ResponseEntity<Response<Book>> createBook(@RequestBody Book book){
        Response<Book> response = new Response<>();
        String message = String.format(SuccessMessageConstant.INSERT_SUCCESS, "book");
        response.setMessage(message);
        response.setData(bookService.addBook(book));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
        @GetMapping("/{bookId}")
        public ResponseEntity<Response<Book>>  getBookById(@PathVariable String bookId){
            Response<Book> response = new Response<>();
            response.setMessage(SuccessMessageConstant.GET_DATA_SUCCESSFUL);
            response.setData(bookService.getBookById(bookId));
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
        @GetMapping
        public List<Book> getAllBook(){
            return bookService.getAllBook();
        }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
        @DeleteMapping("/{bookId}")
        public ResponseEntity <Response> deleteBook(@PathVariable("bookId") String bookId){
            Response<Book> response = new Response<>();
            response.setCode(HttpStatus.GONE.value());
            response.setStatus(HttpStatus.GONE.name());
            response.setMessage(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL);
            response.setTimestamp(LocalDateTime.now());
            response.setData(bookService.deleteBook(bookId));
            return ResponseEntity.status(HttpStatus.GONE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
    @GetMapping("/page")
        public PageResponseWrapper<Book> getAllBookPerPage( @RequestBody BookSearchDto bookSearchDto,
                                                            @RequestParam(name="page", defaultValue ="0") Integer page,
                                                            @RequestParam(name="size", defaultValue = "2") Integer size,
                                                            @RequestParam(name="sortBy" , defaultValue = "id") String sortBy,
                                                            @RequestParam(name="direction", defaultValue = "ASC") String direction){

        Sort sort =Sort.by(Sort.Direction.fromString(direction),sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Book> bookPage = bookService.searchBookPerPage(pageable, bookSearchDto);
        Integer code = HttpStatus.OK.value();
        String status = HttpStatus.OK.name();
        String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
        return new PageResponseWrapper<Book>(code,status,message,bookPage);
    }
}


