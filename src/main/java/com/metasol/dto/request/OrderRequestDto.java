package com.metasol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metasol.dto.response.OrderDetailResponseDto;
import com.metasol.entity.CustomerEntity;
import com.metasol.entity.OrderDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private CustomerEntity customer;

    //    private List<ProductsOfOrder> listProduct;
    @JsonProperty("order_details")
    private List<OrderDetailRequestDto> orderDetailsDtoList;


}
