package com.metasol.services;

import com.metasol.dto.request.ProductImageRequestDto;
import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductImageResponseDto;
import com.metasol.dto.response.ProductResponseDto;
import com.metasol.entity.ProductEntity;
import com.metasol.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto findProductById(long id) throws Exception;
    ProductEntity getProductById(long id) throws Exception;


    Page<ProductResponseDto> getAllProduct(PageRequest pageRequest);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productDTO);

    void deleteProduct(Long id);

    boolean existsByName(String name);

    List<ProductImage> createProductImg(Long productId, List<MultipartFile> files) throws Exception;

}
