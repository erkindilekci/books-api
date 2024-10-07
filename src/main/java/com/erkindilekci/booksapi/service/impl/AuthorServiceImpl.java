package com.erkindilekci.booksapi.service.impl;

import com.erkindilekci.booksapi.domain.Author;
import com.erkindilekci.booksapi.repository.AuthorRepository;
import com.erkindilekci.booksapi.repository.BookRepository;
import com.erkindilekci.booksapi.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author saveAuthor(Author author) {
        if (author.getAge() <= 0 || author.getName().isBlank()) {
            return null;
        }
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Page<Author> findAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
