package com.metasol.dto.response;

import com.metasol.entity.ProductEntity;
import com.metasol.entity.TypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponseDto {
    private TypeEntity type;
    private ProductEntity product;
    private float price;

}
