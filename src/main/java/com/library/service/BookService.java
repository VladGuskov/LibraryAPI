package com.library.service;

import com.library.domain.Book;
import com.library.domain.Library;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.*;
import com.library.request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublishingHouseRepository publishingHouseRepository;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            LibraryRepository libraryRepository,
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            PublishingHouseRepository publishingHouseRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.publishingHouseRepository = publishingHouseRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getOne(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    public Book createDefault(Book book) {
        book.setPublicationDate(LocalDateTime.now());
        return bookRepository.save(book);
    }

    public Book create(Book book, Long libraryId, Long authorId, Long genreId, Long pubHouseId) {
        book.setPublicationDate(LocalDateTime.now());
        Library library = libraryRepository.findById(libraryId).
                orElse(libraryRepository.findById(43L)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Library", libraryId)
                        ));
        book.setLibrary(library);
        book.getAuthors().add(authorRepository.findById(authorId).orElseThrow(
                () -> new ResourceNotFoundException("Author", authorId)
        ));
        book.getGenres().add(genreRepository.findById(genreId).orElseThrow(
                () -> new ResourceNotFoundException("Genre", genreId)
        ));
        book.getPubHouses().add(publishingHouseRepository.findById(pubHouseId).orElseThrow(
                () -> new ResourceNotFoundException("PublishingHouse", pubHouseId)
        ));
        return bookRepository.save(book);
    }

    public Book update(Book book, Long id) {
        return bookRepository.findById(id).map((bookToUpdate) -> {
            bookToUpdate.setName(book.getName());
            bookToUpdate.setDescription(book.getDescription());
            bookToUpdate.setPageQuantity(book.getPageQuantity());
            bookToUpdate.setHasPictures(book.isHasPictures());
            return bookRepository.save(bookToUpdate);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Book", id)
        );
    }

    public ResponseEntity delete(Long id) {
        return bookRepository.findById(id).map((bookToDelete) -> {
            bookRepository.delete(bookToDelete);
            return ResponseEntity.ok("Book id=" + id + " deleted");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Book", id)
        );
    }

    public Book linkToLibrary(BookRequest request) {
        Long libraryId = request.getId();
        Long bookId = request.getBookId();;
        return bookRepository.findById(bookId).map(book -> {
            book.setLibrary(libraryRepository.findById(libraryId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Library", libraryId)
            ));
            return bookRepository.save(book);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
    }

    public Book linkToAuthor(BookRequest request) {
        Long authorId = request.getId();
        Long bookId = request.getBookId();;
        return bookRepository.findById(bookId).map(book -> {
            book.getAuthors().add(authorRepository.findById(authorId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Author", authorId)
                    ));
            return bookRepository.save(book);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
    }

    public Book linkToGenre(BookRequest request) {
        Long genreId = request.getId();
        Long bookId = request.getBookId();;
        return bookRepository.findById(bookId).map(book -> {
            book.getGenres().add(genreRepository.findById(genreId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Genre", genreId)
                    ));
            return bookRepository.save(book);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
    }

    public Book linkToPublishingHouse(BookRequest request) {
        Long pubHouseId = request.getId();
        Long bookId = request.getBookId();;
        return bookRepository.findById(bookId).map(book -> {
            book.getPubHouses().add(publishingHouseRepository.findById(pubHouseId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("PublishingHouse", pubHouseId)
                    ));
            return bookRepository.save(book);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        );
    }
}