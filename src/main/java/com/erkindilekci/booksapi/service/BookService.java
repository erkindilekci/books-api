package com.erkindilekci.booksapi.service;

import com.erkindilekci.booksapi.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book saveBook(String isbn, Book book);

    List<Book> findAllBooks();

    Page<Book> findAllBooks(Pageable pageable);

    Optional<Book> findBookByIsbn(String isbn);

    boolean isExistsByIsbn(String isbn);

    void deleteBookByIsbn(String isbn);
}
