package com.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.Book;
import com.library.domain.view.Views;
import com.library.request.BookRequest;
import com.library.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XE on 16.09.2019
 * @project LibraryAPI
 */

@RestController
@RequestMapping("book")
@Api
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "View a list of available books",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of books"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of books"),
            @ApiResponse(code = 404, message = "Books are not found")
    })
    @GetMapping
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @ApiOperation(value = "View a book with authors, genres and publishing houses and all their params", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view a book"),
            @ApiResponse(code = 404, message = "Book is not found")
    })
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.FullBook.class)
    public Book getOne(@PathVariable("id") Long id) {
        return bookService.getOne(id);
    }

    @ApiOperation(value = "Create a book without links", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book without links creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public Book createDefault(
            @RequestBody Book book
    ) {
        return bookService.createDefault(book);
    }

    @ApiOperation(value = "Create a book with all links", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book with all links creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping("{libraryId}/{authorId}/{genreId}/{pubHouseId}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullBook.class)
    public Book create(
            @PathVariable("libraryId") Long libraryId,
            @PathVariable("authorId") Long authorId,
            @PathVariable("genreId") Long genreId,
            @PathVariable("pubHouseId") Long pubHouseId,
            @RequestBody Book book
    ) {
        return bookService.create(book, libraryId, authorId, genreId, pubHouseId);
    }

    @ApiOperation(value = "Link a book with library",response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book with library linking"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Library or book is not found")
    })
    @PostMapping("/library")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullBook.class)
    public Book linkToLibrary(@RequestBody BookRequest request) {
        return bookService.linkToLibrary(request);
    }

    @ApiOperation(value = "Link a book with author",response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book with author linking"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Author or book is not found")
    })
    @PostMapping("/author")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullBook.class)
    public Book linkToAuthor(@RequestBody BookRequest request) {
        return bookService.linkToAuthor(request);
    }

    @ApiOperation(value = "Link a book with genre",response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book with genre linking"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Genre or book is not found")
    })
    @PostMapping("/genre")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullBook.class)
    public Book linkToGenre(@RequestBody BookRequest request) {
        return bookService.linkToGenre(request);
    }

    @ApiOperation(value = "Link a book with publishing house",response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book with publishing house linking"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Publishing house or book is not found")
    })
    @PostMapping("/pubhouse")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullBook.class)
    public Book linkToPublishingHouse(@RequestBody BookRequest request) {
        return bookService.linkToPublishingHouse(request);
    }

    @ApiOperation(value = "Update a book",response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book updating"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Book is not found")
    })
    @PutMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public Book update(
            @PathVariable("id") Long id,
            @RequestBody Book book
    ) {
        return bookService.update(book, id);
    }

    @ApiOperation(value = "Delete a book",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully book deleting"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Book is not found")
    })
    @DeleteMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return bookService.delete(id);
    }
}
