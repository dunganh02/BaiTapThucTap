package com.metasol.services;

import com.metasol.dto.request.CategoryRequestDto;
import com.metasol.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryDto);
    CategoryResponseDto getCategoryById(Long id);
    List<CategoryResponseDto> getAllCategory();
    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDto);
    void deleteCategory(long id);


}
