package com.metasol.rest;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.CustomerRequestDto;
import com.metasol.dto.response.CustomerListResponseDto;
import com.metasol.dto.response.CustomerResponseDto;
import com.metasol.services.Imp.CustomerServiceImp;
import com.metasol.utils.EOResponse;
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
    EOResponse<CustomerListResponseDto> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        if (page < 0 || limit <= 0) {
            return EOResponse.build(ErrorCode.ERROR_CODE, MessageCode.NOT_NULL);
        }

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());

        Page<CustomerResponseDto> customerPage = customerService.getAll(pageRequest);
        int totalPage = customerPage.getTotalPages();
        List<CustomerResponseDto> customers = customerPage.getContent();

        return EOResponse.build(CustomerListResponseDto
                .builder()
                .customers(customers)
                .totalPages(totalPage)
                .build());
    }

    @GetMapping("/all2")
    EOResponse<List<CustomerResponseDto>> getAll(){
        List<CustomerResponseDto> responseDtoList = customerService.getAll2();

    return EOResponse.build(responseDtoList);

    }

    @GetMapping(value = "/get-by-id/{id}")
    ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(customerService.getById(id));
    }


    @PostMapping("/create")
    EOResponse<?> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            List<String> messError = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return EOResponse.build(messError);
        }

        CustomerResponseDto responseDto = customerService.create(requestDto);
        return EOResponse.build(responseDto);
    }

    @PutMapping("{id}")
    EOResponse<CustomerResponseDto> update(@NotNull @PathVariable(name = "id") Long id,
                                               @NotNull @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto responseDto = customerService.update(id, requestDto);
        return EOResponse.build(responseDto);
    }

    @DeleteMapping("{id}")
    EOResponse<?> delete(@PathVariable(name = "id") Long id) {
        customerService.delete(id);
        return EOResponse.build("Xoa thanh cong customer id: " + id);
    }


}
