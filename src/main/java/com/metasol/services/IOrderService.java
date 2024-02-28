package com.metasol.services;

import com.metasol.dto.request.OrderRequestDto;
import com.metasol.dto.response.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IOrderService {
    OrderResponseDto create(OrderRequestDto dto);
    OrderResponseDto create2(OrderRequestDto dto);
    OrderResponseDto update(Long id, OrderRequestDto dto);
    void delete(Long id);
    OrderResponseDto getById(Long id);
    Page<OrderResponseDto> getAll(PageRequest pageRequest);

    
}
