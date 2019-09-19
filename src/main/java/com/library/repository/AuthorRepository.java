package com.library.repository;

import com.library.domain.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author XE on 17.09.2019
 * @project LibraryAPI
 */

public interface AuthorRepository extends JpaRepository<Author,Long> {
    @EntityGraph(value = "author_books", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Author> findById(Long id);
}
