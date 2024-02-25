package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.CategoryRequestDto;
import com.metasol.dto.response.CategoryResponseDto;
import com.metasol.entity.CategoryEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.ICategoryRepository;
import com.metasol.services.ICategoryService;
import com.metasol.services.mapper.CategoryResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements ICategoryService {

    private final ICategoryRepository categoryRepo;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryDto) {
        CategoryEntity entity = new CategoryEntity();
        this.setValue(entity, categoryDto);
        categoryRepo.save(entity);
        return categoryResponseMapper.entityToResponse(entity);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        CategoryEntity entity = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException(""));
        return categoryResponseMapper.entityToResponse(entity);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        List<CategoryEntity> entityList = categoryRepo.findAll();
        List<CategoryResponseDto> dto = new LinkedList<>();
        for (CategoryEntity entity: entityList) {
            CategoryResponseDto responseDto = categoryResponseMapper.entityToResponse(entity);
            dto.add(responseDto);
        }
        return dto;
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDto) {
        CategoryEntity entity = categoryRepo.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        this.setValue(entity, categoryDto);
        categoryRepo.save(entity);
        return categoryResponseMapper.entityToResponse(entity);
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepo.deleteById(id);
    }

    private void setValue(CategoryEntity entity, CategoryRequestDto dto) {
        entity.setName(dto.getName());
    }
}
