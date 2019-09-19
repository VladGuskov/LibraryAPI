package com.library.repository;

import com.library.domain.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author XE on 17.09.2019
 * @project LibraryAPI
 */

public interface GenreRepository extends JpaRepository<Genre,Long> {
    @EntityGraph(value = "genre_books", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Genre> findById(Long id);
}
