package com.metasol.dto.response;

import lombok.*;

import java.util.List;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListResponseDto {
    private List<OrderResponseDto> orders;
    private int totalPage;

}
