package com.enigma.bookit.service.implementation;

import com.enigma.bookit.entity.Book;
import com.enigma.bookit.entity.PackageChosen;
import com.enigma.bookit.entity.Payment;
import com.enigma.bookit.repository.BookRepository;
import com.enigma.bookit.service.BookService;
import com.enigma.bookit.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PaymentService paymentService;

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
        if(LocalDateTime.now().isAfter(book.getActiveUntil())){
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
    public List<Integer> getCapacity(String id, LocalDateTime start, LocalDateTime stop) {
        return bookRepository.countCap(id, start, stop);
    }

    @Override
    public void extendBook(String bookId, PackageChosen packageChosen) {
        Book book = bookRepository.getById(bookId);
        Payment payment = paymentService.getById(book.getPayment().getId());
        payment.setPackageChosen(packageChosen);
        payment.setPaymentDate(null);
        payment.setBookingStart(book.getActiveUntil());
        payment.setPaymentStatus(false);
        paymentService.save(payment);
    }


}
