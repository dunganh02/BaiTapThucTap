package com.metasol.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metasol.entity.OrderDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDto {
    @JsonProperty("order_id")
    private Long oderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("number_of_product")
    private int numberOfProduct;

    private double price;

    public OrderDetailResponseDto(OrderDetailEntity entity){
        if(entity != null){
            this.oderId = entity.getOrder().getId();
            this.productId = entity.getProduct().getId();
            this.numberOfProduct = entity.getNumberOfProduct();
            this.price = entity.getPrice();
        }
    }
}
