package com.metasol.services.mapper;

import com.metasol.dto.response.ProductResponseDto;
import com.metasol.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {
    ProductResponseDto entityToResponse(ProductEntity entity);
}
