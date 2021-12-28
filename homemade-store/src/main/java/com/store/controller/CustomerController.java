package com.store.controller;

import com.store.dto.CustomerDto;
import com.store.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity
                .ok()
                .body(service.create(customerDto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Customer", notes = "Get a Customer based on the Id received in the request")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Customer with the entered Id does not exist")})
    public ResponseEntity<CustomerDto> get(@PathVariable Long id) {
        if (service.getOne(id) == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(service.getOne(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(){
        return ResponseEntity
                .ok()
                .body(service.getAllCustomers());
    }
}
