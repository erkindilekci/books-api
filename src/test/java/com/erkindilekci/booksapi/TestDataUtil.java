package com.erkindilekci.booksapi;

import com.erkindilekci.booksapi.domain.Author;
import com.erkindilekci.booksapi.domain.Book;
import com.erkindilekci.booksapi.dto.AuthorDto;
import com.erkindilekci.booksapi.dto.BookDto;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Liam Smith")
                .age(45)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Emma Johnson")
                .age(30)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Noah Brown")
                .age(60)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .name("Liam Smith")
                .age(45)
                .build();
    }

    public static AuthorDto createTestAuthorDtoB() {
        return AuthorDto.builder()
                .id(2L)
                .name("Emma Johnson")
                .age(30)
                .build();
    }

    public static AuthorDto createTestAuthorDtoC() {
        return AuthorDto.builder()
                .id(3L)
                .name("Noah Brown")
                .age(60)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("978-3-16-148410-0")
                .title("Mystery of the Old House")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("978-3-16-148411-7")
                .title("Journey to the Unknown")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("978-3-16-148412-4")
                .title("Secrets of the Forest")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-3-16-148410-0")
                .title("Mystery of the Old House")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoB(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-3-16-148411-7")
                .title("Journey to the Unknown")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoC(final AuthorDto author) {
        return BookDto.builder()
                .isbn("978-3-16-148412-4")
                .title("Secrets of the Forest")
                .author(author)
                .build();
    }
}
