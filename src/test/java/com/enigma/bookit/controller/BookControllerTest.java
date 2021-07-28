package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.BookSearchDto;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.BookRepository;
import com.enigma.bookit.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)
@Import(BookController.class)

class BookControllerTest {
    @MockBean
    BookService service;

    @Autowired
    BookController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookRepository repository;

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void createBook() throws Exception {
        Book book = new Book();
        Payment payment =new Payment();
        payment.setId("a");
        book.setId("c1");
        book.setPayment(payment);
        when(service.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post(ApiUrlConstant.BOOK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(book)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id", Matchers.is(book.getId())));
    }



    @Test
    void getBookById() throws Exception {
        Book book = new Book();
        Payment payment =new Payment();
        payment.setId("a");
        book.setId("c1");
        book.setPayment(payment);
        when(service.addBook(any(Book.class))).thenReturn(book);

        when(service.getBookById("c1")).thenReturn(book);

        RequestBuilder request = get("/api/book/c1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book));

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id", Matchers.is(book.getId())));
    }

    @Test
    void getAllBook() throws Exception {
        Book book = new Book();
        Payment payment =new Payment();
        payment.setId("a");
        book.setId("c1");
        book.setPayment(payment);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        when(service.getAllBook()).thenReturn(bookList);

        mockMvc.perform(get(ApiUrlConstant.BOOK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(bookList)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(book.getId())));
        }

    @Test
    void deleteBook() throws Exception {
        Book book = new Book();
        book.setId("delete01");

        when(service.deleteBook(book.getId())).thenReturn(null);

        mockMvc.perform(delete(ApiUrlConstant.BOOK+"/id="+book.getId()))
                .andExpect(jsonPath("$.code",is(HttpStatus.GONE.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())));
    }
    @SneakyThrows
    @Test
    void getCustomerPerPage() throws Exception {

        Book book = new Book();
        Payment payment =new Payment();
        payment.setId("a");
        book.setId("c1");
        book.setPayment(payment);

        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setActiveUntil(null);


        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        Page<Book> bookPage = new Page<Book>() {

            @Override
            public Iterator<Book> iterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Book> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super Book, ? extends U> function) {
                return null;
            }
        };

            when(service.searchBookPerPage(any(), any())).thenReturn(bookPage);

        mockMvc.perform(get(ApiUrlConstant.BOOK+"/page?page=0&size=2&sortBy=id&direction=ASC")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(book)).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                .andExpect(jsonPath("$.data",is(bookSearchDto.getActiveUntil())));
    }

}