package com.metasol.dto.response;

import lombok.*;

import java.util.List;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDto {
    private List<ProductResponseDto> products;
    private int totalPages;
}
