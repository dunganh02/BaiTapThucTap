package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductResponseDto;
import com.metasol.entity.ProductEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.IProductRepository;
import com.metasol.services.IProductService;
import com.metasol.services.mapper.ProductResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductServiceImp implements IProductService {
    private final IProductRepository productRepos;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        ProductEntity entity = new ProductEntity();
        this.value(entity, requestDto);
        productRepos.save(entity);
        return productResponseMapper.entityToResponse(entity);
    }

    @Override
    public ProductResponseDto getProductById(long id) {

        ProductEntity entity = productRepos.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        return productResponseMapper.entityToResponse(entity);
    }

    @Override
    public Page<ProductResponseDto> getAllProduct(PageRequest pageRequest) {
        Page<ProductEntity> liProduct = productRepos.findAll(pageRequest);

        return liProduct.map(this::convertToResponseDto);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDTO) {
        ProductEntity entity = productRepos.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        this.value(entity, productDTO);
        productRepos.save(entity);
        return productResponseMapper.entityToResponse(entity);
    }

    @Override
    public void deleteProduct(Long id) {
            productRepos.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    private void value(ProductEntity entity, ProductRequestDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUnit(dto.getUnit());
        entity.setCategory(dto.getCategory());
    }

    private ProductResponseDto convertToResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUnit(entity.getUnit());
        dto.setCategory(entity.getCategory());
        return dto;

    }
}

