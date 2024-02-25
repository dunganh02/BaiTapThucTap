package com.metasol.services.mapper;

import com.metasol.dto.response.CustomerResponseDto;
import com.metasol.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {
     CustomerResponseDto entityToResponse(CustomerEntity entity);
}
