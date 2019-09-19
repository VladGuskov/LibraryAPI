package com.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.view.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
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
        name = "pub_house_books",
        attributeNodes = @NamedAttributeNode(value = "books", subgraph = "params"),
        subgraphs = @NamedSubgraph(name = "params", attributeNodes = {
                @NamedAttributeNode("library"),
                @NamedAttributeNode("authors"),
                @NamedAttributeNode("genres")
        })
)
public class PublishingHouse {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @JsonView(Views.Id.class)
        private Long id;

        @JsonView(Views.IdName.class)
        private String name;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
        @JsonView(Views.IdNameParams.class)
        private Date creationDate;

        @JsonView(Views.IdNameParams.class)
        private String country;

        @JsonView(Views.IdNameParams.class)
        private boolean workStatus;

        @ManyToMany
        @JoinTable(
                name = "book_pub_houses",
                joinColumns = @JoinColumn(name = "pub_house_id"),
                inverseJoinColumns = @JoinColumn(name = "book_id")
        )
        @JsonView(Views.FullPublishingHouse.class)
        private Set<Book> books = new HashSet<>();
}
