package com.store.controller;

import com.store.dto.OrderItemDto;
import com.store.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    @Autowired
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItemDto>>getOrderItemsForOrder(@PathVariable Long orderId){
        return ResponseEntity
                .ok()
                .body(orderItemService.getOrderItemsForOrder(orderId));
    }
}
