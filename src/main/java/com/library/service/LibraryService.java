package com.library.service;

import com.library.domain.Book;
import com.library.domain.Library;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.LibraryRepository;
import com.library.request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public List<Library> getAll() {
        return libraryRepository.findAll();
    }

    public Library getOne(Long id) {
        return libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Library", id));
    }

    public Library create(Library library) {
        return libraryRepository.save(library);
    }

    public Library update(Library library, Long id) {
        return libraryRepository.findById(id).map((libraryToUpdate) -> {
            libraryToUpdate.setName(library.getName());
            return libraryRepository.save(libraryToUpdate);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Library", id)
        );
    }

    public ResponseEntity delete(Long id) {
        return libraryRepository.findById(id).map((libraryToDelete) -> {
            libraryRepository.delete(libraryToDelete);
            return ResponseEntity.ok("Library id=" + id + " deleted");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Library", id)
        );
    }

    public ResponseEntity put(BookRequest request) {
        Long libraryId = request.getId();
        Long bookId = request.getBookId();
        return libraryRepository.findById(libraryId).map(library -> {
            library.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                    () -> new ResourceNotFoundException("Book", bookId)
            ));
            libraryRepository.save(library);
            return ResponseEntity.ok("Book id=" + bookId + " was successfully added to the Library id=" + library + "!!!");
        }).orElseThrow(
                () -> new ResourceNotFoundException("Library", libraryId)
        );
    }
}
