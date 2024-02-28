package com.metasol.rest;

import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductListResponseDto;
import com.metasol.dto.response.ProductResponseDto;
import com.metasol.services.Imp.ProductServiceImp;
import com.metasol.utils.EOResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class RestProductController {
    private final ProductServiceImp productService;

    @GetMapping(value = "get-by-id/{id}")
    EOResponse<ProductResponseDto> getById(@PathVariable(name = "id") Long id) {
        ProductResponseDto dto = productService.getProductById(id);
        return EOResponse.build(dto);

    }

    @GetMapping()
    EOResponse<ProductListResponseDto> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "limit", defaultValue = "10") int limit) {

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());

        Page<ProductResponseDto> productPage = productService.getAllProduct(pageRequest);

        int totalPage = productPage.getTotalPages();
        List<ProductResponseDto> products = productPage.getContent();


        return EOResponse.build(ProductListResponseDto
                .builder()
                .products(products)
                .totalPages(totalPage)
                .build());
    }

    @PostMapping("/create")
    EOResponse<ProductResponseDto> create(@RequestBody ProductRequestDto dto) {
        ProductResponseDto responseDto = productService.createProduct(dto);
        return EOResponse.build(responseDto);
    }

    @PutMapping("/{id}")
    EOResponse<ProductResponseDto> update(@PathVariable(name = "id") Long id,
                                              @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.updateProduct(id, requestDto);
        return EOResponse.build(responseDto);
    }

    @DeleteMapping("/{id}")
    EOResponse<String> delete(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return EOResponse.build("Đã xóa product id: " + id);
    }
}
