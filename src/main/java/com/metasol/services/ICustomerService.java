package com.metasol.services;

import com.metasol.dto.request.CustomerRequestDto;
import com.metasol.dto.request.search.CustomerSearchDto;
import com.metasol.dto.response.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ICustomerService {
    CustomerResponseDto getById(Long id);
    CustomerResponseDto create(CustomerRequestDto requestDto);
    CustomerResponseDto update(Long id, CustomerRequestDto requestDto);
    void delete(Long id);
    Page<CustomerResponseDto> getAll(PageRequest pageRequest);
    List<CustomerResponseDto> getAll2();

}
