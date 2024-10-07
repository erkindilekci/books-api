package com.erkindilekci.booksapi.service;

import com.erkindilekci.booksapi.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author saveAuthor(Author author);

    List<Author> findAllAuthors();

    Page<Author> findAllAuthors(Pageable pageable);

    Optional<Author> findAuthorById(Long id);

    boolean existsById(Long id);

    void deleteAuthorById(Long id);
}
