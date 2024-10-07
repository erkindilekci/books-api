package com.erkindilekci.booksapi.controller;

import com.erkindilekci.booksapi.TestDataUtil;
import com.erkindilekci.booksapi.domain.Book;
import com.erkindilekci.booksapi.dto.BookDto;
import com.erkindilekci.booksapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private BookService bookService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(BookService bookService, MockMvc mockMvc) {
        this.bookService = bookService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnHttpStatus201WhenCreateBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldReturnSavedBookWhenCreateBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc
                .perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-3-16-148410-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Mystery of the Old House"));
    }

    @Test
    public void shouldReturnHttpStatus200WhenListBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnListOfBooksWhenListBooks() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        bookService.saveBook(book.getIsbn(), book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].isbn").value("978-3-16-148410-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("Mystery of the Old House"));
    }

    @Test
    public void shouldReturnHttpStatus200WhenGetBookById() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        bookService.saveBook(book.getIsbn(), book);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/978-3-16-148410-0"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnHttpStatus404WhenNoBookExists() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        bookService.saveBook(book.getIsbn(), book);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnBookWhenBookExists() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        bookService.saveBook(book.getIsbn(), book);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/books/978-3-16-148410-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-3-16-148410-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Mystery of the Old House"));
    }

    @Test
    public void shouldReturnHttpStatus200WhenUpdateBookById() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.saveBook(book.getIsbn(), book);

        savedBook.setTitle("UPDATED");
        String updatedBookJson = objectMapper.writeValueAsString(savedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnUpdatedBookWhenUpdateBookById() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        Book savedBook = bookService.saveBook(book.getIsbn(), book);

        savedBook.setTitle("UPDATED");
        String updatedBookJson = objectMapper.writeValueAsString(savedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED"));
    }

    @Test
    public void shouldReturnHttpStatus204WhenDeleteBookById() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        bookService.saveBook(book.getIsbn(), book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldReturnHttpStatus404WhenDeleteNonExistingBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldVerifyBookIsDeleted() throws Exception {
        Book book = TestDataUtil.createTestBookA(null);
        bookService.saveBook(book.getIsbn(), book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
