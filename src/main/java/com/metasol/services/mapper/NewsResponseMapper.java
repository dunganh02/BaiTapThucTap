package com.metasol.services.mapper;

import com.metasol.dto.response.NewResponseDto;
import com.metasol.entity.NewsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsResponseMapper {
    NewResponseDto entityToResponse(NewsEntity entity);
}
