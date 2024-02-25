package com.metasol.services;

import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(long id) throws Exception;

    Page<ProductResponseDto> getAllProduct(PageRequest pageRequest);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productDTO);

    void deleteProduct(Long id);

    boolean existsByName(String name);

//    ProductImage createProductIng(Long productId,ProductImageDTO productImageDTO) throws Exception;

}
