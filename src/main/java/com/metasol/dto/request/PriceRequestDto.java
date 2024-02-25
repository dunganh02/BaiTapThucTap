package com.metasol.dto.request;

import com.metasol.entity.ProductEntity;
import com.metasol.entity.TypeEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceRequestDto {
    private TypeEntity type;
    private ProductEntity product;
    private float price;
}
