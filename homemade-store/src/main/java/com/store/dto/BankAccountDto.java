package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {

    private Long bankAccountCVV;
    private String cardNumber;
    private String accountNumber;
    private double balance;
}
