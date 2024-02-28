package com.metasol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metasol.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequestDto {

    private ProductEntity products;

    @JsonProperty("number_of_product")
    private int numberOfProduct;

}
