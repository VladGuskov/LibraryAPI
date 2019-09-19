package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */

public interface BookRepository extends JpaRepository<Book,Long> {
    @EntityGraph(attributePaths = { "authors", "genres", "pubHouses" })
    Optional<Book> findById(Long id);
}
