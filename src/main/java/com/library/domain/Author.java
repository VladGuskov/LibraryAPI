package com.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.view.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
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
        name = "author_books",
        attributeNodes = @NamedAttributeNode(value = "books", subgraph = "params"),
        subgraphs = @NamedSubgraph(name = "params", attributeNodes = {
                @NamedAttributeNode("library"),
                @NamedAttributeNode("genres"),
                @NamedAttributeNode("pubHouses")
        })
)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonView(Views.IdNameParams.class)
    private Date dateOfBirthday;

    @JsonView(Views.IdNameParams.class)
    private String country;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonView(Views.FullAuthor.class)
    private Set<Book> books = new HashSet<>();
}
