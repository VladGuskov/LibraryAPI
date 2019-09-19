package com.library.domain;

import com.fasterxml.jackson.annotation.*;
import com.library.domain.view.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

    @Column(length = 500)
    @JsonView(Views.IdNameParams.class)
    private String description;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.IdNameParams.class)
    private LocalDateTime publicationDate;

    @JsonView(Views.IdNameParams.class)
    private int pageQuantity;

    @JsonView(Views.IdNameParams.class)
    private boolean hasPictures;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @JsonView({
            Views.FullBook.class,
            Views.FullAuthor.class,
            Views.FullGenre.class,
            Views.FullPublishingHouse.class
    })
    private Library library;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonView({
            Views.FullBook.class,
            Views.FullLibrary.class,
            Views.FullGenre.class,
            Views.FullPublishingHouse.class
    })
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonView({
            Views.FullBook.class,
            Views.FullLibrary.class,
            Views.FullAuthor.class,
            Views.FullPublishingHouse.class
    })
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "book_pub_houses",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "pub_house_id")
    )
    @JsonView({
            Views.FullBook.class,
            Views.FullLibrary.class,
            Views.FullAuthor.class,
            Views.FullGenre.class
    })
    private Set<PublishingHouse> pubHouses = new HashSet<>();
}
