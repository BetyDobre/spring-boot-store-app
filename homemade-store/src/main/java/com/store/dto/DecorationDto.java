package com.store.dto;

import com.store.domain.DecorationCategory;
import com.store.validator.OnlyLetters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecorationDto {

    @NotNull
    private Long decorationId;

    @NotNull
    @OnlyLetters
    private String decorationName;

    @NotEmpty
    private double price;

    @Size(min = 0)
    private String description;

    private DecorationCategory category;
}
