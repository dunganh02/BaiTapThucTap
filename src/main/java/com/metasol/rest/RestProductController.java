package com.metasol.rest;

import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductListResponseDto;
import com.metasol.dto.response.ProductResponseDto;
import com.metasol.services.Imp.ProductServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class RestProductController {
    private final ProductServiceImp productService;

    @GetMapping(value = "get-by-id/{id}")
    ResponseEntity<ProductResponseDto> getById(@PathVariable(name = "id") Long id) {
        ProductResponseDto dto = productService.getProductById(id);
        return ResponseEntity.ok().body(dto);

    }

    @GetMapping()
    ResponseEntity<ProductListResponseDto> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "limit", defaultValue = "10") int limit) {
        if (page < 0 || limit <= 0) {
            return ResponseEntity.badRequest().build();
        }

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());

        Page<ProductResponseDto> productPage = productService.getAllProduct(pageRequest);

        int totalPage = productPage.getTotalPages();
        List<ProductResponseDto> products = productPage.getContent();


        return ResponseEntity.ok(ProductListResponseDto
                .builder()
                .products(products)
                .totalPages(totalPage)
                .build());
    }

    @PostMapping("/create")
    ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto dto) {
        ProductResponseDto responseDto = productService.createProduct(dto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductResponseDto> update(@PathVariable(name = "id") Long id,
                                              @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Đã xóa product id: " + id);
    }
}
