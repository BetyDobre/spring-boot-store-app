package com.store.controller;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.dto.CartDto;
import com.store.dto.CustomerDto;
import com.store.dto.DecorationDto;
import com.store.dto.OrderItemDto;
import com.store.mapper.CustomerMapper;
import com.store.service.CartService;
import com.store.service.CustomerService;
import com.store.service.DecorationService;
import com.store.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final DecorationService decorationService;
    private final OrderService orderService;
    private final CartService cartService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CartController(DecorationService decorationService, OrderService orderService, CartService cartService, CustomerService customerService, CustomerMapper customerMapper) {
        this.decorationService = decorationService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.cartService = cartService;
        this.customerMapper = customerMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CartDto> addDecorationToCart(@RequestParam Long customerId, @RequestParam Long decorationId, @RequestParam int quantity) {
        CustomerDto customerDto = customerService.getOne(customerId);
        if(customerDto == null){
            return  ResponseEntity
                    .notFound()
                    .build();
        }

        DecorationDto decorationDto = decorationService.getOne(decorationId);
        if(decorationDto == null){
            return  ResponseEntity
                    .notFound()
                    .build();
        }

        OrderItemDto orderItemDto = new OrderItemDto(quantity, decorationDto.getPrice(), decorationId);

        return ResponseEntity
                .ok()
                .body(cartService.add(customerDto, orderItemDto));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<List<OrderItemDto>> deleteItemFromCart(@RequestParam Long customerId, @RequestParam Long decorationId) {
        CustomerDto customerDto = customerService.getOne(customerId);
        if(customerDto == null){
            return  ResponseEntity
                    .notFound()
                    .build();
        }

        Customer customer = customerMapper.mapToEntity(customerDto);
        Cart cart = cartService.findCartByCustomer(customer);
        if(cart == null){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok()
                .body(cartService.deleteItemFromCart(cart, customerId, decorationId));
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts(){
        return ResponseEntity
                .ok()
                .body(cartService.getAll());
    }

    @GetMapping("/content/{customerId}")
    public ResponseEntity<List<OrderItemDto>> getCartContent(@PathVariable Long customerId) {
        return ResponseEntity
                .ok()
                .body(cartService.getCartContent(customerId));
    }
}
