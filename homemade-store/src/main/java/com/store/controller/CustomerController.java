package com.store.controller;

import com.store.domain.*;
import com.store.domain.security.Authority;
import com.store.dto.CustomerDto;
import com.store.mapper.CustomerMapper;
import com.store.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        return "login-error";
    }

    @GetMapping(path = "/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model){
        model.addAttribute("customerDto", new CustomerDto());

        return "registerForm";
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @RequestBody @ModelAttribute CustomerDto customerDto, BindingResult bindingResult){
        ModelAndView viewRegister = new ModelAndView("registerForm");
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return viewRegister;
        }

        Customer customer = customerMapper.mapToEntity(customerDto);
        Set<Authority> authorities = new HashSet<>();
        //authorities.add(roleRepository.findRoleByRoleId(1L));
//        customer.setRoles(authorities);
//        customer.setEnabled(true);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.register(customer);

        ModelAndView model= new ModelAndView("login");

        return model;
    }

    @GetMapping("/customers")
    public ModelAndView getCustomers(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("customers");
        List<Customer> customers = customerService.getAllCustomers();
        modelAndView.addObject("customers", customers);

        return modelAndView;
    }

    @RequestMapping("/customers/delete/{id}")
    @Transactional
    public String deleteByCardNumber(@PathVariable Long id){
        customerService.deleteById(id);

        return "redirect:/customers";
    }

    @RequestMapping("/customers/update/{id}")
    public String update(@PathVariable Long id, Model model){
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);

        return "updateCustomerForm";
    }

    @PostMapping("/customers/update")
    public String updateDecoration(@Valid @ModelAttribute Customer customer,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return "updateCustomerForm";
        }

        Set<Authority> authorities = new HashSet<>();
       // authorities.add(roleRepository.findRoleByRoleId(1L));
//        customer.setRoles(authorities);
//        customer.setEnabled(true);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.register(customer);

        return "redirect:/customers";
    }
}
