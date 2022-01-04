package com.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    private String cardNumber;
    private int bankAccountCVV;
    private double balance;
    @JsonIgnore
    private Customer customer;
}
