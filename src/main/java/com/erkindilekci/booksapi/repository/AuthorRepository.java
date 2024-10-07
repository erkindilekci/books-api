package com.erkindilekci.booksapi.repository;

import com.erkindilekci.booksapi.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {

    Iterable<Author> findAllByAgeLessThan(int age);

    Iterable<Author> findAllByAgeGreaterThan(int age);

    @Query("select a from Author a where a.name like ?1%")
    Iterable<Author> findAllByNameStartWith(String prefix);
}
