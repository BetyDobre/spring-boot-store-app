package com.store.controller;

import com.store.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }
}
