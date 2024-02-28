package com.metasol.rest;

import com.metasol.dto.request.OrderRequestDto;
import com.metasol.dto.response.OrderListResponseDto;
import com.metasol.dto.response.OrderResponseDto;
import com.metasol.services.Imp.OrderServiceImp;
import com.metasol.utils.EOResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@AllArgsConstructor
public class RestOrderController {
    private final OrderServiceImp orderService;

    @GetMapping("/get-by-id/{id}")
    EOResponse<OrderResponseDto> getById(@PathVariable(name = "id") Long id) {
        OrderResponseDto responseDto = orderService.getById(id);

        return EOResponse.build(responseDto);
    }

    @GetMapping("/getAll")
    EOResponse<OrderListResponseDto> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "limit", defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id").ascending());

        Page<OrderResponseDto> orderPage = orderService.getAll(pageRequest);

        int totalPage = orderPage.getTotalPages();
        List<OrderResponseDto> listOrderDetail = orderPage.getContent();

        return EOResponse.build(OrderListResponseDto.builder()
                .orders(listOrderDetail)
                .totalPage(totalPage)
                .build());
    }

    @PostMapping("/create-order")
    EOResponse<OrderResponseDto> create(@RequestBody OrderRequestDto dto) {
        OrderResponseDto responseDto = orderService.create(dto);

        return EOResponse.build(responseDto);
    }

    @PutMapping("/update/{id}")
    EOResponse<OrderResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody OrderRequestDto dto) {
        OrderResponseDto responseDto = orderService.update(id, dto);

        return EOResponse.build(responseDto);
    }

    @DeleteMapping("/{id}")
    EOResponse<?> delete(@PathVariable(name = "id") Long id) {
        orderService.delete(id);
        return EOResponse.build("Xoa Thanh Cong id: " + id);
    }
}
