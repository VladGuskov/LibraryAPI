package com.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.Author;
import com.library.domain.view.Views;
import com.library.request.BookRequest;
import com.library.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XE on 17.09.2019
 * @project LibraryAPI
 */

@RestController
@RequestMapping("author")
@Api
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value = "View a list of available authors",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of authors"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of authors"),
            @ApiResponse(code = 404, message = "Authors are not found")
    })
    @GetMapping
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @ApiOperation(value = "View an author with books and all their params",response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully author retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view an author"),
            @ApiResponse(code = 404, message = "Author is not found")
    })
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.FullAuthor.class)
    public Author getOne(@PathVariable("id") Long id) {
        return authorService.getOne(id);
    }

    @ApiOperation(value = "Create an author without book link",response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully author without book link creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public Author createDefault(
            @RequestBody Author author
    ) {
        return authorService.createDefault(author);
    }

    @ApiOperation(value = "Create an author with book link",response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully author with book link creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullAuthor.class)
    public Author create(
            @PathVariable("id") Long bookId,
            @RequestBody Author author
    ) {
        return authorService.create(author, bookId);
    }

    @ApiOperation(value = "Link an author with book",response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully author with book linking"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Author or book is not found")
    })
    @PostMapping("/book")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullAuthor.class)
    public ResponseEntity put(@RequestBody BookRequest request) {
        return authorService.put(request);
    }

    @ApiOperation(value = "Update an author",response = Author.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully author updating"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Author is not found")
    })
    @PutMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public Author update(
            @PathVariable("id") Long id,
            @RequestBody Author author
    ) {
        return authorService.update(author, id);
    }

    @ApiOperation(value = "Delete an author",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully author deleting"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Author is not found")
    })
    @DeleteMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return authorService.delete(id);
    }
}
