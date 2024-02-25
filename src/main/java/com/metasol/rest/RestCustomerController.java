package com.metasol.rest;

import com.metasol.dto.request.CustomerRequestDto;
import com.metasol.dto.response.CustomerListResponseDto;
import com.metasol.dto.response.CustomerResponseDto;
import com.metasol.services.Imp.CustomerServiceImp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/customers")
@AllArgsConstructor
public class RestCustomerController {
    private final CustomerServiceImp customerService;

    @GetMapping
    ResponseEntity<CustomerListResponseDto> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        if (page < 0 || limit <= 0) {
            return ResponseEntity.badRequest().build();
        }

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());

        Page<CustomerResponseDto> customerPage = customerService.getAll(pageRequest);
        int totalPage = customerPage.getTotalPages();
        List<CustomerResponseDto> customers = customerPage.getContent();

        return ResponseEntity.ok(CustomerListResponseDto
                .builder()
                .customers(customers)
                .totalPages(totalPage)
                .build());
    }

    @GetMapping("/all2")
    ResponseEntity<List<CustomerResponseDto>> getAll(){
        List<CustomerResponseDto> responseDtoList = customerService.getAll2();

    return ResponseEntity.ok().body(responseDtoList);

    }

    @GetMapping(value = "/get-by-id/{id}")
    ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(customerService.getById(id));
    }


    @PostMapping("/create")
    ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            List<String> messError = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(messError);
        }

        CustomerResponseDto responseDto = customerService.create(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("{id}")
    ResponseEntity<CustomerResponseDto> update(@NotNull @PathVariable(name = "id") Long id,
                                               @NotNull @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto responseDto = customerService.update(id, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().body("Xoa thanh cong customer id: " + id);
    }


}
