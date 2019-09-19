package com.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.Genre;
import com.library.domain.view.Views;
import com.library.request.BookRequest;
import com.library.service.GenreService;
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
@RequestMapping("genre")
@Api
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ApiOperation(value = "View a list of available genres",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of genres"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of genres"),
            @ApiResponse(code = 404, message = "Genre is not found")
    })
    @GetMapping
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    @ApiOperation(value = "View a genre with books and all their params",response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully genre retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view a genre"),
            @ApiResponse(code = 404, message = "Genre is not found")
    })
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.FullGenre.class)
    public Genre getOne(@PathVariable("id") Long id) {
        return genreService.getOne(id);
    }

    @ApiOperation(value = "Create a genre without book link",response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully genre without book link creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public Genre createDefault(
            @RequestBody Genre genre
    ) {
        return genreService.createDefault(genre);
    }

    @ApiOperation(value = "Create a genre with book link",response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully genre with book link creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullGenre.class)
    public Genre create(
            @PathVariable("id") Long bookId,
            @RequestBody Genre genre
    ) {
        return genreService.create(genre, bookId);
    }

    @ApiOperation(value = "Link a genre with book",response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully genre with book linking"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Genre or book is not found")
    })
    @PostMapping("/book")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullGenre.class)
    public ResponseEntity put(@RequestBody BookRequest request) {
        return genreService.put(request);
    }

    @ApiOperation(value = "Update a genre",response = Genre.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully genre updating"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Genre is not found")
    })
    @PutMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public Genre update(
            @PathVariable("id") Long id,
            @RequestBody Genre genre
    ) {
        return genreService.update(genre, id);
    }

    @ApiOperation(value = "Delete a genre",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully genre deleting"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Genre is not found")
    })
    @DeleteMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return genreService.delete(id);
    }
}
