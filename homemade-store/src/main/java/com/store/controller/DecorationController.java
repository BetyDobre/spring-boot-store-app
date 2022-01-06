package com.store.controller;

import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.service.DecorationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/decorations", tags = "Decorations")
@RequestMapping("/decorations")
public class DecorationController {

    @Autowired
    private final DecorationService service;

    public DecorationController(DecorationService service) {
        this.service = service;
    }


    @PostMapping("/{category}")
    @ApiOperation(value = "Create a decoration",
            notes = "Create a new decoration based on the information received in the request in a specific category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The decoration was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<DecorationDto> addDecoaration(@RequestBody DecorationDto decorationDto, @PathVariable String category) {
        return ResponseEntity
                .ok()
                .body(service.add(decorationDto, category));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a decoration",
            notes = "Get a decoration based on the Id received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The decoration with the entered Id does not exist!")
    })
    public ResponseEntity<DecorationDto> get(@PathVariable Long id) {
        if (service.getOne(id) == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(service.getOne(id));
    }

    @GetMapping("/filter/{category}")
    @ApiOperation(value = "Get decorations from a specific category",
            notes = "Get decorations from a specific category received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The decorations with the entered category don't exist!")
    })
    public ResponseEntity<List<DecorationDto>> getDecorationByCategory(@PathVariable DecorationCategory category) {
        if (service.getByCategory(category) == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(service.getByCategory(category));
    }

    @GetMapping
    @ApiOperation(value = "Get all decorations",
            notes = "Get all decorations from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The data was retrieved successfully"),
            @ApiResponse(code = 404, message = "No decorations were found")
    })
    public ResponseEntity<List<DecorationDto>> getAll(){
        return ResponseEntity
                .ok()
                .body(service.getAllDecorations());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit a decoration",
            notes = "Modify data for a specific decorations based on the Id received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The data was retrieved successfully"),
            @ApiResponse(code = 404, message = "Decoration with the entered id was not found")
    })
    public ResponseEntity<DecorationDto> update(@RequestBody DecorationDto decorationDto, @PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(service.update(decorationDto, id));
    }
}
