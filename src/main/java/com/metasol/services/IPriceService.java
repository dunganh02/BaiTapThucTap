package com.metasol.services;

import com.metasol.dto.request.CategoryRequestDto;
import com.metasol.dto.request.PriceRequestDto;
import com.metasol.dto.response.CategoryResponseDto;
import com.metasol.dto.response.PriceResponseDto;

import java.util.List;

public interface IPriceService {
    PriceResponseDto createPrice(PriceRequestDto dto);
    PriceResponseDto getPriceById(Long id);
    List<PriceResponseDto> getAllPrice();
    PriceResponseDto updatePrice(Long id, PriceRequestDto dto);
    void deletePrice(long id);
}
