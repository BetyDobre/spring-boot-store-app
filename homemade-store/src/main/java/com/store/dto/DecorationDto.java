package com.store.dto;

import com.store.domain.DecorationCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecorationDto {

    private Long decorationId;
    private String decorationName;
    private double price;
    private DecorationCategory category;
    private String description;
}
