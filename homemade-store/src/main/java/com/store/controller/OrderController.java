package com.store.controller;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.repository.CustomerRepository;
import com.store.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CustomerRepository customerRepository;

    public OrderController(OrderService service, CustomerRepository customerRepository) {
        this.orderService = service;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        Order orderFound = orderService.findOrderById(id);
        model.addAttribute("order", orderFound);

        return "orderDetails";
    }

    @GetMapping("/all")
    public ModelAndView getOrdersByUser(Principal principal) {
        //User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Customer customer = customerRepository.findCustomerByCustomerId(1L);

        ModelAndView modelAndView = new ModelAndView("orders");
        List<Order> ordersFound = orderService.getOrdersByCustomer(customer);
        modelAndView.addObject("orders", ordersFound);

        return modelAndView;
    }

}
