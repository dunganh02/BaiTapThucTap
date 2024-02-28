package com.metasol.dto.request;

import com.metasol.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsOfOrder {
    private ProductEntity productId;
    private int numberOfProduct;
}
