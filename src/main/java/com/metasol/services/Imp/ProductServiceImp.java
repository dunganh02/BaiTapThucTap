package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.ProductImageRequestDto;
import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductImageResponseDto;
import com.metasol.dto.response.ProductResponseDto;
import com.metasol.entity.ProductEntity;
import com.metasol.entity.ProductImage;
import com.metasol.exception.EOException;
import com.metasol.repositories.IProductImageRepository;
import com.metasol.repositories.IProductRepository;
import com.metasol.services.IProductService;
import com.metasol.services.mapper.ProductImageResponseMapper;
import com.metasol.services.mapper.ProductResponseMapper;
import com.metasol.utils.FileImage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImp implements IProductService {
    private final IProductRepository productRepos;
    private final IProductImageRepository productImageRepos;
    private final ProductResponseMapper productResponseMapper;
    private final ProductImageResponseMapper productImageResponseMapper;
    private FileImage uploadFile;
//    private ProductImage productImage;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        ProductEntity entity = new ProductEntity();
        this.value(entity, requestDto);
        productRepos.save(entity);
        return productResponseMapper.entityToResponse(entity);
    }

    @Override
    public ProductResponseDto findProductById(long id) {
        ProductEntity entity = productRepos.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        return productResponseMapper.entityToResponse(entity);
    }

    @Override
    public ProductEntity getProductById(long idProduct) throws Exception {
        Optional<ProductEntity> optionalProduct = productRepos.getDetailProduct(idProduct);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new RuntimeException();
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

    @Override
    public List<ProductImage> createProductImg(Long productId, List<MultipartFile> files) throws Exception {
        // check xem có id product đó không
        ProductEntity existingProduct = getProductById(productId);

        // Xử lý Multi file khi truyền xuống
        files = files == null ? new ArrayList<MultipartFile>() : files;

        if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new EOException(ErrorCode.UPLOAD_IMAGES_MAX_5, MessageCode.INVALID_VALUE);
        }
        ProductImage newProductImage = null;
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }
            // kiểm tra kích thước file và định dạng > 10MB
            if (file.getSize() > 10 * 2024 * 1024) {
                throw new EOException(ErrorCode.PAYLOAD_TOO_LARGE, MessageCode.INVALID_VALUE);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new EOException(ErrorCode.PAYLOAD_TOO_LARGE, MessageCode.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE);
            }
            String fileName = uploadFile.storeFile2(file);
            // Lưu đường dẫn url vào db

            if (existingProduct != null) {
                newProductImage = new ProductImage();
                this.valueImage(newProductImage, existingProduct, fileName);
                productImageRepos.save(newProductImage);
                productImages.add(newProductImage);
            } else {
                throw new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND);
            }
        }

        return productImages;
    }

    private void valueImage(ProductImage productImag, ProductEntity product, String fileName) {
        productImag.setProduct(product);
        productImag.setImageUrl(fileName);
    }

    private void value(ProductEntity entity, ProductRequestDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUnit(dto.getUnit());
        entity.setCategory(dto.getCategory());
    }

    /**
     * @param entity : ProductEntity
     * @return : dto Product
     */
    private ProductResponseDto convertToResponseDto(ProductEntity entity) {

        ProductResponseDto dto = new ProductResponseDto();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUnit(entity.getUnit());
        dto.setCategory(entity.getCategory());
        return dto;

    }
}

