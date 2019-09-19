package com.library.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.view.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author XE on 17.09.2019
 * @project LibraryAPI
 */

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name"})
@NamedEntityGraph(
        name = "genre_books",
        attributeNodes = @NamedAttributeNode(value = "books", subgraph = "params"),
        subgraphs = @NamedSubgraph(name = "params", attributeNodes = {
                @NamedAttributeNode("library"),
                @NamedAttributeNode("authors"),
                @NamedAttributeNode("pubHouses")
        })
)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonView(Views.IdNameParams.class)
    private int ageLimit;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonView(Views.FullGenre.class)
    private Set<Book> books = new HashSet<>();
}
