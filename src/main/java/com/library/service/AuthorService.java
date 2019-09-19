package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Library;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
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
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getOne(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", id));
    }

    public Author createDefault(Author author) {
        return authorRepository.save(author);
    }

    public Author create(Author author, Long bookId) {
        author.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                    () -> new ResourceNotFoundException("Book", bookId)
        ));
        return authorRepository.save(author);
    }

    public Author update(Author author, Long id) {
        return authorRepository.findById(id).map((authorToUpdate) -> {
            authorToUpdate.setName(author.getName());
            authorToUpdate.setDateOfBirthday(author.getDateOfBirthday());
            authorToUpdate.setCountry(author.getCountry());
            return authorRepository.save(authorToUpdate);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Author", id)
        );
    }

    public ResponseEntity delete(Long id) {
        return authorRepository.findById(id).map((authorToDelete) -> {
            authorRepository.delete(authorToDelete);
            return ResponseEntity.ok("Author id=" + id + " deleted");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Author", id)
        );
    }

    public ResponseEntity put(BookRequest request) {
        Long authorId = request.getId();
        Long bookId = request.getBookId();
        return authorRepository.findById(authorId).map(author -> {
            author.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                    () -> new ResourceNotFoundException("Book", bookId)
            ));
            authorRepository.save(author);
            return ResponseEntity.ok("Book id=" + bookId + " was successfully added to the Author id=" + authorId + "!!!");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Author", authorId)
        );
    }
}
