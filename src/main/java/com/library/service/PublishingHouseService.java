package com.library.service;

import com.library.domain.PublishingHouse;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.PublishingHouseRepository;
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
public class PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PublishingHouseService(PublishingHouseRepository publishingHouseRepository, BookRepository bookRepository) {
        this.publishingHouseRepository = publishingHouseRepository;
        this.bookRepository = bookRepository;
    }

    public List<PublishingHouse> getAll() {
        return publishingHouseRepository.findAll();
    }

    public PublishingHouse getOne(Long id) {
        return publishingHouseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre", id));
    }

    public PublishingHouse createDefault(PublishingHouse publishingHouse) {
        return publishingHouseRepository.save(publishingHouse);
    }

    public PublishingHouse create(PublishingHouse publishingHouse, Long bookId) {
        publishingHouse.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", bookId)
        ));
        return publishingHouseRepository.save(publishingHouse);
    }

    public PublishingHouse update(PublishingHouse publishingHouse, Long id) {
        return publishingHouseRepository.findById(id).map((publishingHouseToUpdate) -> {
            publishingHouseToUpdate.setName(publishingHouse.getName());
            publishingHouseToUpdate.setCreationDate(publishingHouse.getCreationDate());
            publishingHouseToUpdate.setCountry(publishingHouse.getCountry());
            publishingHouseToUpdate.setWorkStatus(publishingHouse.isWorkStatus());
            return publishingHouseRepository.save(publishingHouseToUpdate);
        }).orElseThrow(
                () -> new ResourceNotFoundException("PublishingHouse", id)
        );
    }

    public ResponseEntity delete(Long id) {
        return publishingHouseRepository.findById(id).map((publishingHouseToDelete) -> {
            publishingHouseRepository.delete(publishingHouseToDelete);
            return ResponseEntity.ok("PublishingHouse id=" + id + " deleted");
        }).orElseThrow(
                () -> new ResourceNotFoundException("PublishingHouse", id)
        );
    }

    public ResponseEntity put(BookRequest request) {
        Long pubHouseId = request.getId();
        Long bookId = request.getBookId();
        return publishingHouseRepository.findById(pubHouseId).map(pubHouse -> {
            pubHouse.getBooks().add(bookRepository.findById(bookId).orElseThrow(
                    () -> new ResourceNotFoundException("Book", bookId)
            ));
            publishingHouseRepository.save(pubHouse);
            return ResponseEntity.ok("Book id=" + bookId + " was successfully added to the PublishingHouse id=" + pubHouseId + "!!!");
        }).orElseThrow(
                () -> new ResourceNotFoundException("PublishingHouse", pubHouseId)
        );
    }
}
