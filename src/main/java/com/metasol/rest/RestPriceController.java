package com.metasol.rest;

import com.metasol.dto.request.PriceRequestDto;
import com.metasol.dto.response.PriceResponseDto;
import com.metasol.services.Imp.PriceServiceImp;
import com.metasol.utils.EOResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/prices")
@AllArgsConstructor
public class RestPriceController {
    private final PriceServiceImp priceService;
    @PostMapping("")
    EOResponse<?> create(@Valid @RequestBody PriceRequestDto dto,
                         BindingResult result) {
        PriceResponseDto price = priceService.createPrice(dto);
        return EOResponse.build(price);
    }

    @GetMapping("/get-all")
    EOResponse<List<?>> getAll() {
        List<PriceResponseDto> listPrice = priceService.getAllPrice();
        return EOResponse.build(listPrice);
    }


    @PutMapping("/{id}")
    EOResponse<PriceResponseDto> updateCategory(@PathVariable(value = "id") Long id,
                                                       @RequestBody PriceRequestDto request) {
        PriceResponseDto responseDto = priceService.updatePrice(id, request);
        return EOResponse.build(responseDto);
    }

    @DeleteMapping("/{id}")
    EOResponse<String> deleteCategory(@PathVariable(value = "id") Long id) {
        try {
            priceService.deletePrice(id);
            return EOResponse.build("Xóa thành công id" + id);

        } catch (Exception e) {
            return EOResponse.build(e.getMessage());
        }

    }
}
