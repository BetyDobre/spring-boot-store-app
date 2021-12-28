package com.store.controller;

import com.store.domain.Decoration;
import com.store.domain.DecorationCategory;
import com.store.dto.DecorationDto;
import com.store.service.DecorationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/decorations")
public class DecorationController {

    @Autowired
    private final DecorationService service;

    public DecorationController(DecorationService service) {
        this.service = service;
    }


    @PostMapping("/{category}")
    public ResponseEntity<DecorationDto> addDecoaration(@RequestBody DecorationDto decorationDto, @PathVariable String category) {
        return ResponseEntity
                .ok()
                .body(service.add(decorationDto, category));
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<List<DecorationDto>> getAll(){
        return ResponseEntity
                .ok()
                .body(service.getAllDecorations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DecorationDto> update(@RequestBody DecorationDto decorationDto, @PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(service.update(decorationDto, id));
    }
}
