package com.library.repository;

import com.library.domain.Library;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */

public interface LibraryRepository extends JpaRepository<Library,Long> {
    @EntityGraph(value = "books_for_library", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Library> findById(Long id);
}
