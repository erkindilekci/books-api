package com.erkindilekci.booksapi.repository;

import com.erkindilekci.booksapi.TestDataUtil;
import com.erkindilekci.booksapi.domain.Author;
import com.erkindilekci.booksapi.service.AuthorService;
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
public class AuthorRepositoryIntegrationTest {

    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorService authorService, MockMvc mockMvc) {
        this.authorService = authorService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnHttpStatus201WhenCreateAuthor() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void shouldReturnSavedAuthorWhenCreateAuthor() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Liam Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45));
    }

    @Test
    public void shouldReturnHttpStatus200WhenListAuthors() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnListOfAuthorsWhenListAuthors() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(author);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/authors")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Liam Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].age").value(45));
    }

    @Test
    public void shouldReturnHttpStatus200WhenGetAuthorById() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(author);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/authors/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnHttpStatus404WhenFindAuthorAndNoAuthorExists() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(author);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/authors/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnAuthorWhenFindAuthorAndAuthorExists() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        authorService.saveAuthor(author);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/authors/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Liam Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45));
    }

    @Test
    public void shouldReturnHttpStatus200WhenUpdateAuthorById() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.saveAuthor(author);
        savedAuthor.setName("UPDATED");
        String authorJson = objectMapper.writeValueAsString(savedAuthor);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnHttpStatus404WhenUpdateAuthorAndNoAuthorExists() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.saveAuthor(author);
        savedAuthor.setName("UPDATED");
        String authorJson = objectMapper.writeValueAsString(savedAuthor);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/authors/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnUpdatedAuthorWhenUpdateAuthorAndAuthorExists() throws Exception {
        Author author = TestDataUtil.createTestAuthorA();
        Author savedAuthor = authorService.saveAuthor(author);
        savedAuthor.setName("UPDATED");
        String authorJson = objectMapper.writeValueAsString(savedAuthor);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UPDATED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45));
    }
}
