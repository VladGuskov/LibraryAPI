package com.library.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.library.domain.PublishingHouse;
import com.library.domain.view.Views;
import com.library.request.BookRequest;
import com.library.service.PublishingHouseService;
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
@RequestMapping("pubhouse")
@Api
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    @Autowired
    public PublishingHouseController(PublishingHouseService publishingHouseService) {
        this.publishingHouseService = publishingHouseService;
    }

    @ApiOperation(value = "View a list of available publishing houses",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of publishing houses"),
            @ApiResponse(code = 401, message = "You are not authorized to view list of publishing houses"),
            @ApiResponse(code = 404, message = "Publishing houses are not found")
    })
    @GetMapping
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public List<PublishingHouse> getAll() {
        return publishingHouseService.getAll();
    }

    @ApiOperation(value = "View a publishing house with books and all their params",response = PublishingHouse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully publishing house retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view a publishing houses"),
            @ApiResponse(code = 404, message = "Publishing house is not found")
    })
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(Views.FullPublishingHouse.class)
    public PublishingHouse getOne(@PathVariable("id") Long id) {
        return publishingHouseService.getOne(id);
    }

    @ApiOperation(value = "Create a publishing house with without book link",response = PublishingHouse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully publishing house without book link creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public PublishingHouse createDefault(
            @RequestBody PublishingHouse publishingHouse
    ) {
        return publishingHouseService.createDefault(publishingHouse);
    }

    @ApiOperation(value = "Create a publishing house with book link",response = PublishingHouse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully publishing house with book link creation"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this")
    })
    @PostMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullPublishingHouse.class)
    public PublishingHouse create(
            @PathVariable("id") Long bookId,
            @RequestBody PublishingHouse publishingHouse
    ) {
        return publishingHouseService.create(publishingHouse, bookId);
    }

    @ApiOperation(value = "Link a publishing house with book",response = PublishingHouse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully linking publishing house with book"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Publishing house or book is not found")
    })
    @PostMapping("/book")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.FullPublishingHouse.class)
    public ResponseEntity put(@RequestBody BookRequest request) {
        return publishingHouseService.put(request);
    }

    @ApiOperation(value = "Update a publishing house",response = PublishingHouse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully publishing house updating"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Publishing house is not found")
    })
    @PutMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @JsonView(Views.IdNameParams.class)
    public PublishingHouse update(
            @PathVariable("id") Long id,
            @RequestBody PublishingHouse publishingHouse
    ) {
        return publishingHouseService.update(publishingHouse, id);
    }

    @ApiOperation(value = "Delete a publishing house",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully publishing house deleting"),
            @ApiResponse(code = 401, message = "You are not authorized to do this"),
            @ApiResponse(code = 403, message = "Only Admin can do this"),
            @ApiResponse(code = 404, message = "Publishing house is not found")
    })
    @DeleteMapping("{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return publishingHouseService.delete(id);
    }
}
