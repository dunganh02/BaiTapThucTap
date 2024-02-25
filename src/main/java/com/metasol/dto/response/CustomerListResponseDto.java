package com.metasol.dto.response;

import lombok.*;

import java.util.List;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerListResponseDto {
    private List<CustomerResponseDto> customers;
    private int totalPages;
}
