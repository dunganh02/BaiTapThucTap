package com.metasol.services.mapper;

import com.metasol.dto.response.CategoryResponseDto;
import com.metasol.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    CategoryResponseDto entityToResponse(CategoryEntity category);
}
