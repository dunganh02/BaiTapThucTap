package com.metasol.rest;

import com.metasol.dto.request.CategoryRequestDto;
import com.metasol.dto.response.CategoryResponseDto;
import com.metasol.services.Imp.CategoryServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto dto,
                                     BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        CategoryResponseDto category = categoryService.createCategory(dto);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("/get-all")
    ResponseEntity<List<?>> getAll() {
        List<CategoryResponseDto> listCategory = categoryService.getAllCategory();
        return ResponseEntity.ok().body(listCategory);
    }


    @PutMapping("/{id}")
    ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable(value = "id") Long id,
                                                       @RequestBody CategoryRequestDto request) {
        CategoryResponseDto responseDto = categoryService.updateCategory(id, request);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().body("Xóa thành công id" + id);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
