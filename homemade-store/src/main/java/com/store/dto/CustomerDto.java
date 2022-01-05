package com.store.dto;

import com.store.validator.OnlyDigits;
import com.store.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull
    private Long customerId;

    @NotNull
    @OnlyLetters
    private String firstName;

    @NotNull
    @OnlyLetters
    private String lastName;

    @NotEmpty
    @OnlyLetters
    private String address;

    @OnlyDigits
    private String phoneNumber;
}
