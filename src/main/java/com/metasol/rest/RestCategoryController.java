package com.metasol.rest;

import com.metasol.dto.request.CategoryRequestDto;
import com.metasol.dto.response.CategoryResponseDto;
import com.metasol.services.Imp.CategoryServiceImp;
import com.metasol.utils.EOResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor // DI
public class RestCategoryController {

    private final CategoryServiceImp categoryService;

    @PostMapping("")
    EOResponse<?> createCategory(@Valid @RequestBody CategoryRequestDto dto,
                                 BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return EOResponse.build(errorMessage);
        }
        CategoryResponseDto category = categoryService.createCategory(dto);
        return EOResponse.build(category);
    }

    @GetMapping("/get-all")
    EOResponse<List<?>> getAll() {
        List<CategoryResponseDto> listCategory = categoryService.getAllCategory();
        return EOResponse.build(listCategory);
    }


    @PutMapping("/{id}")
    EOResponse<CategoryResponseDto> updateCategory(@PathVariable(value = "id") Long id,
                                                   @RequestBody CategoryRequestDto request) {
        CategoryResponseDto responseDto = categoryService.updateCategory(id, request);
        return EOResponse.build(responseDto);
    }

    @DeleteMapping("/{id}")
    EOResponse<String> deleteCategory(@PathVariable(value = "id") Long id) {
        try {
            categoryService.deleteCategory(id);
            return EOResponse.build("Xóa thành công id" + id);

        } catch (Exception e) {
            return EOResponse.build(e.getMessage());
        }

    }
}
