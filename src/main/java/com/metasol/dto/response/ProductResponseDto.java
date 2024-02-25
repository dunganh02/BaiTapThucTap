package com.metasol.dto.response;


import com.metasol.entity.CategoryEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private String name;

    private String description;

    private String unit;

    private CategoryEntity category;
}
