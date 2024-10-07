package com.erkindilekci.booksapi.service.impl;

import com.erkindilekci.booksapi.domain.Book;
import com.erkindilekci.booksapi.repository.BookRepository;
import com.erkindilekci.booksapi.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExistsByIsbn(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
