package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.library.domain.view.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name"})
@NamedEntityGraph(
        name = "books_for_library",
        attributeNodes = @NamedAttributeNode(value = "books", subgraph = "params"),
        subgraphs = @NamedSubgraph(name = "params", attributeNodes = {
                @NamedAttributeNode("authors"),
                @NamedAttributeNode("genres"),
                @NamedAttributeNode("pubHouses")
        })
)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @OneToMany(mappedBy = "library", orphanRemoval = true)
    @JsonView(Views.FullLibrary.class)
    private Set<Book> books = new HashSet<>();
}
