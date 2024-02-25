package com.metasol.services.mapper;

import com.metasol.dto.response.PriceResponseDto;
import com.metasol.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
    PriceResponseDto entityToResponse(PriceEntity entity);

}
