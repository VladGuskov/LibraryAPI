package com.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.Library;
import com.library.domain.view.Views;
import com.library.service.LibraryService;
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
@RequestMapping("library")
@Api
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @ApiOperation(value = "View a list of available libraries",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of libraries"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of libraries"),
            @ApiResponse(code = 404, message = "Libraries are not found")
    })
    @GetMapping
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.IdName.class)
    public List<Library> getAll() {
        return libraryService.getAll();
    }

    @ApiOperation(value = "View a library with books and all their params",response = Library.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully library retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view a library"),
            @ApiResponse(code = 404, message = "Library is not found")
    })
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.FullLibrary.class)
    public Library getOne(@PathVariable("id") Long id) {
        return libraryService.getOne(id);
    }

    @ApiOperation(value = "Create a library",response = Library.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully library creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdName.class)
    public Library create(
            @RequestBody Library library
    ) {
        return libraryService.create(library);
    }

    @ApiOperation(value = "Update a library",response = Library.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully library updating"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Library is not found")
    })
    @PutMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullLibrary.class)
    public Library update(
            @PathVariable("id") Long id,
            @RequestBody Library library
    ) {
        return libraryService.update(library, id);
    }

    @ApiOperation(value = "Delete a library",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully library deleting"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Library is not found")
    })
    @DeleteMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return libraryService.delete(id);
    }
}
