package com.store.controller;

import com.store.dto.OrderDto;
import com.store.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService service) {
        this.orderService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(orderService.getOne(id));
    }

    @GetMapping("/all/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrdersFromCustomer(@PathVariable Long customerId) {
        return ResponseEntity
                .ok()
                .body(orderService.getOrdersByCustomer(customerId));
    }
}
