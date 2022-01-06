package com.store.controller;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.dto.*;
import com.store.mapper.CustomerMapper;
import com.store.service.CartService;
import com.store.service.CustomerService;
import com.store.service.DecorationService;
import com.store.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Api(value = "/carts", tags = "Cart")
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
    @ApiOperation(value = "Add a decoration to the cart",
            notes = "Add a decoration received in the request to the cart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The decoration was successfully added to the cart, returning Cart details"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
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
    @ApiOperation(value = "Delete decoration from the cart",
            notes = "Delete the decoration received in the request from the cart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The decoration was successfully deleted from the Cart, returning Cart details"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
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
    @ApiOperation(value = "Get all carts from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The data retrieved successfully"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<List<CartDto>> getAllCarts(){
        return ResponseEntity
                .ok()
                .body(cartService.getAll());
    }

    @ApiOperation(value = "Get cart for a customer",
            notes = "Get the cart for the customer received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    @GetMapping("/content/{customerId}")
    public ResponseEntity<List<OrderItemDto>> getCartContent(@PathVariable Long customerId) {
        return ResponseEntity
                .ok()
                .body(cartService.getCartContent(customerId));
    }

    @PostMapping("/checkout/{customerId}")
    @ApiOperation(value = "Checkout cart for a customer",
            notes = "Checkout all decorations from the cart and pay with the account received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The order was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<OrderDto> checkout(@PathVariable Long customerId, @RequestParam String cardNumber) {
        return ResponseEntity
                .ok()
                .body(orderService.createOrder(customerId, cartService.getCartItems().get(customerId), cardNumber));

    }
}
