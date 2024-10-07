package com.erkindilekci.booksapi.repository;

import com.erkindilekci.booksapi.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<Book, String>, PagingAndSortingRepository<Book, String> {
}
