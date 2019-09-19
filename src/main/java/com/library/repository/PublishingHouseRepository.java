package com.library.repository;

import com.library.domain.PublishingHouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author XE on 17.09.2019
 * @project LibraryAPI
 */

public interface PublishingHouseRepository extends JpaRepository<PublishingHouse,Long> {
    @EntityGraph(value = "pub_house_books", type = EntityGraph.EntityGraphType.LOAD)
    Optional<PublishingHouse> findById(Long id);
}
