package com.store.controller;

import com.store.service.DecorationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decorations")
public class DecorationController {
    private final DecorationService service;

    public DecorationController(DecorationService service) {
        this.service = service;
    }
}
