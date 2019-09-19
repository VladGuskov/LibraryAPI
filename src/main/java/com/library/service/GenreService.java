package com.library.service;

import com.library.domain.Genre;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.GenreRepository;
import com.library.request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XE on 17.09.2019
 * @project LibraryAPI
 */

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre getOne(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre", id));
    }

    public Genre createDefault(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre create(Genre genre, Long bookId) {
        genre.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        ));
        return genreRepository.save(genre);
    }

    public Genre update(Genre genre, Long id) {
        return genreRepository.findById(id).map((genreToUpdate) -> {
            genreToUpdate.setName(genre.getName());
            genreToUpdate.setAgeLimit(genre.getAgeLimit());
            return genreRepository.save(genreToUpdate);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Genre", id)
        );
    }

    public ResponseEntity delete(Long id) {
        return genreRepository.findById(id).map((genreToDelete) -> {
            genreRepository.delete(genreToDelete);
            return ResponseEntity.ok("Genre id=" + id + " deleted");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Genre", id)
        );
    }

    public ResponseEntity put(BookRequest request) {
        Long genreId = request.getId();
        Long bookId = request.getBookId();
        return genreRepository.findById(genreId).map(genre -> {
            genre.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                    () -> new ResourceNotFoundException("Book", bookId)
            ));
            genreRepository.save(genre);
            return ResponseEntity.ok("Book id=" + bookId + " was successfully added to the Genre id=" + genreId + "!!!");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Genre", genreId)
        );
    }
}
