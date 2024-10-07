package com.erkindilekci.booksapi.controller;

import com.erkindilekci.booksapi.domain.Book;
import com.erkindilekci.booksapi.dto.BookDto;
import com.erkindilekci.booksapi.mapper.Mapper;
import com.erkindilekci.booksapi.service.BookService;
import jakarta.servlet.ServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private Mapper<Book, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        Book book = bookMapper.mapFrom(bookDto);
        boolean exists = bookService.isExistsByIsbn(isbn);

        Book savedBook = bookService.saveBook(isbn, book);
        BookDto savedBookDto = bookMapper.mapTo(savedBook);

        return exists
                ? new ResponseEntity<>(savedBookDto, HttpStatus.OK)
                : new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.findAllBooks(pageable);
        Page<BookDto> page = books.map(bookMapper::mapTo);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("isbn") String isbn) {
        Optional<Book> book = bookService.findBookByIsbn(isbn);
        return book.isPresent()
                ? new ResponseEntity<>(bookMapper.mapTo(book.get()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn, ServletRequest servletRequest) {
        if (!bookService.isExistsByIsbn(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBookByIsbn(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
