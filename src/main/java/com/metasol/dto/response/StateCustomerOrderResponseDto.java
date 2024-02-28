package com.metasol.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateCustomerOrderResponseDto {
    private Long id;
    private String name;
    private int total_quantity_sold;
    private double total_money;
}
