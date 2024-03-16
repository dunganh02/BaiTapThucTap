package com.metasol.rest;

import com.metasol.dto.request.ProductRequestDto;
import com.metasol.dto.response.ProductListResponseDto;
import com.metasol.dto.response.ProductResponseDto;
import com.metasol.services.Imp.ProductServiceImp;
import com.metasol.utils.EOResponse;
import com.metasol.utils.FileImage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class RestProductController {
    private final ProductServiceImp productService;
    private FileImage fileImage;

    @GetMapping(value = "get-by-id/{id}")
    EOResponse<ProductResponseDto> getById(@PathVariable(name = "id") Long id) {
        ProductResponseDto dto = productService.findProductById(id);
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

    @PostMapping("/image/uploads/{id}")
    EOResponse<?> uploadImages(
            @PathVariable(name = "id") Long id,
            @ModelAttribute("files") List<MultipartFile> files
    ) throws Exception {

        return EOResponse.build(productService.createProductImg(id, files));
    }

    @GetMapping(value = "/product/{image_name}")
    public ResponseEntity<?> getImageById(@PathVariable(name = "image_name") String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.png").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

//        return EOResponse.build(fileImage.viewImage(imageName));
    }


}
