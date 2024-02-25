package com.metasol.rest;

import com.metasol.dto.request.CategoryRequestDto;
import com.metasol.dto.request.TypeRequestDto;
import com.metasol.dto.response.CategoryResponseDto;
import com.metasol.dto.response.TypeResponseDto;
import com.metasol.services.Imp.TypeServiceImp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/types")
public class RestTypeController {
    private final TypeServiceImp typeService;


    @GetMapping("/get-all")
    ResponseEntity<List<?>> getAll() {
        List<TypeResponseDto> listType = typeService.getAll();
        return ResponseEntity.ok().body(listType);
    }

    @PostMapping("")
    ResponseEntity<?> createCategory(@Valid @NotNull @RequestBody TypeRequestDto dto,
                                     BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        TypeResponseDto typeResponseDto = typeService.createType(dto);
        return ResponseEntity.ok().body(typeResponseDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateType(@PathVariable(name = "id") Long id, @RequestBody TypeRequestDto requestDto) {

        TypeResponseDto typeResponseDto = typeService.updateType(id, requestDto);
        return ResponseEntity.ok().body(typeResponseDto);

    }

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteType(@PathVariable(name = "id") Long id) {
        try {
            typeService.deleteType(id);
            return ResponseEntity.ok().body("Xoa thanh cong type id: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
