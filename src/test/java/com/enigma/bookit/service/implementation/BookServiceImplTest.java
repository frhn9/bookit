package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.BookSearchDto;
import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {


    @Autowired
    BookServiceImpl service;

    @MockBean
    BookRepository repository;


    @Test
    void addBook() {
        Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,10,00,00));
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,11,00,00));
        book.setPayment(book.getPayment());
        when(repository.save(book)).thenReturn(book);
        service.addBook(book);

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(repository.findAll()).thenReturn(books);
        assertEquals(1, repository.findAll().size());
    }

//
    @Test
    void getBookById() {
        Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,10,00,00));
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,11,00,00));
        book.setPayment(book.getPayment());
        repository.save(book);
        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        Book returned = service.getBookById("c1");
        verify(repository).findById("c1");
        assertNotNull(returned);
    }

    @Test
    void getAllBook() {
        Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,10,00,00));
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,11,00,00));
        book.setPayment(book.getPayment());
        repository.save(book);

        List<Book>books= new ArrayList<>();
        books.add(book);

        when(service.getAllBook()).thenReturn(books);
        assertEquals(1,service.getAllBook().size());
    }

    @Test
    void deleteBook() {
        Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,10,00,00));
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,11,00,00));
        book.setPayment(book.getPayment());
        repository.save(book);
        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        service.deleteBook("c1");
        assertEquals(0,repository.findAll().size());

    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,10,00,00));
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,11,00,00));
        book.setPayment(book.getPayment());
        when(repository.save(book)).thenReturn(book);
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,12,00,00));
        repository.save(book);

        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        service.updateBook(book.getId(), book);
        assertEquals(LocalDateTime.of(2021, Month.JULY,20,12,00,00), repository.findById("c1").get().getActiveUntil());
    }

    @Test
    void getBookPage(){
        Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,10,00,00));
        book.setActiveUntil(LocalDateTime.of(2021, Month.JULY,20,11,00,00));
        book.setPayment(book.getPayment());

        BookSearchDto bookSearchDto = new BookSearchDto();
        bookSearchDto.setActiveFrom(book.getActiveFrom());
        bookSearchDto.setActiveUntil(book.getActiveUntil());

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
        Page<BookSearchDto> bookSearchDtoPage = new Page<BookSearchDto>() {


            @Override
            public Iterator<BookSearchDto> iterator() {
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
            public List<BookSearchDto> getContent() {
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
            public <U> Page<U> map(Function<? super BookSearchDto, ? extends U> function) {
                return null;
            }
        };

            when(repository.findAll((Specification<Book>) any(), any())).thenReturn(bookPage);
        repository.save(book);
        when(service.searchBookPerPage(any(), eq(bookSearchDto))).thenReturn(bookPage);

        assertEquals(LocalDateTime.of(2021, Month.JULY,20,11,00,00), book.getActiveUntil());
    }

    @Test
    void checkActiveBook(){
        Book book = new Book();
        book.setId("c1");
        book.setActiveUntil(LocalDateTime.now().plusHours(1));
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,9,00,00));
        book.setPayment(new Payment());
        repository.save(book);
        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        assertTrue(service.checkActiveBook("c1"));
    }

    @Test
    void checkActiveBook_ShouldTurnFalse(){
        Book book = new Book();
        book.setId("c1");
        book.setActiveUntil(LocalDateTime.now().minusHours(1));
        book.setActiveFrom(LocalDateTime.of(2021, Month.JULY,20,9,00,00));
        book.setPayment(new Payment());
        repository.save(book);
        when(repository.findById(book.getId())).thenReturn(Optional.of(book));
        assertFalse(service.checkActiveBook("c1"));
    }

    @Test
    void countCap() {
        Facility facility = new Facility();
        facility.setId("2f");
        facility.setCapacity(20);
        Payment payment = new Payment();
         payment.setId("1");
         payment.setFacility(facility);
         Book book = new Book();
        book.setId("c1");
        book.setActiveFrom(LocalDateTime.now().minusDays(1));
        book.setActiveUntil(LocalDateTime.now().plusDays(1));
        book.setPayment(payment);
        repository.save(book);
        List<Integer> result = new ArrayList<>();
        result.add(20);
        when(repository.countCap(facility.getId(),book.getActiveUntil(),book.getActiveFrom())).thenReturn(result);
        for (Integer a : result) {
            assertEquals(book.getPayment().getFacility().getCapacity(), a);

        }
    }
}


