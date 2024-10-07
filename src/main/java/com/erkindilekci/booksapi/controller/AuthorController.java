package com.erkindilekci.booksapi.controller;

import com.erkindilekci.booksapi.domain.Author;
import com.erkindilekci.booksapi.dto.AuthorDto;
import com.erkindilekci.booksapi.mapper.Mapper;
import com.erkindilekci.booksapi.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;
    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDto>> getAllAuthors(Pageable pageable) {
        Page<Author> authors = authorService.findAllAuthors(pageable);
        Page<AuthorDto> page = authors.map(authorMapper::mapTo);
        return new ResponseEntity<>(page, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        Optional<Author> author = authorService.findAuthorById(id);
        return author.isPresent()
                ? new ResponseEntity<>(authorMapper.mapTo(author.get()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ) {
        if (!authorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        Author author = authorMapper.mapFrom(authorDto);
        Author savedAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        if (!authorService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
