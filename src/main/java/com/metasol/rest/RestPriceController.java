package com.metasol.rest;

import com.metasol.dto.request.PriceRequestDto;
import com.metasol.dto.response.PriceResponseDto;
import com.metasol.services.Imp.PriceServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/prices")
@AllArgsConstructor
public class RestPriceController {
    private final PriceServiceImp priceService;
    @PostMapping("")
    ResponseEntity<?> create(@Valid @RequestBody PriceRequestDto dto,
                                     BindingResult result) {
        PriceResponseDto price = priceService.createPrice(dto);
        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/get-all")
    ResponseEntity<List<?>> getAll() {
        List<PriceResponseDto> listPrice = priceService.getAllPrice();
        return ResponseEntity.ok().body(listPrice);
    }


    @PutMapping("/{id}")
    ResponseEntity<PriceResponseDto> updateCategory(@PathVariable(value = "id") Long id,
                                                       @RequestBody PriceRequestDto request) {
        PriceResponseDto responseDto = priceService.updatePrice(id, request);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long id) {
        try {
            priceService.deletePrice(id);
            return ResponseEntity.ok().body("Xóa thành công id" + id);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
