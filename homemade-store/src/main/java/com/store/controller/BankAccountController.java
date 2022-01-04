package com.store.controller;

import com.store.dto.BankAccountDto;
import com.store.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/bankAccounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountDto bankAccountDto, @PathVariable Long customerId){
        return ResponseEntity
                .ok()
                .body(bankAccountService.addBankAccount(bankAccountDto, customerId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<BankAccountDto>> getBankAccountsForCustomer(@PathVariable Long customerId) {
        return ResponseEntity
                .ok()
                .body(bankAccountService.getBankAccountsForCustomer(customerId));
    }

    @DeleteMapping("/{customerId}/{cardNumber}")
    @Transactional
    public String deleteBankAccount(@PathVariable Long customerId, @PathVariable String cardNumber, HttpServletResponse response){
        boolean result = bankAccountService.delete(customerId, cardNumber);
        if (result) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return String.format("Bank account for customer with id %s was removed", customerId);
        } else {
            response.setStatus(404);
            return String.format("Bank account for customer with id %s was not removed", customerId);
        }
    }
}
