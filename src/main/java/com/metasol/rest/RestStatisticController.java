package com.metasol.rest;

import com.metasol.dto.response.StatResponseDto;
import com.metasol.dto.response.StateCustomerOrderResponseDto;
import com.metasol.services.Imp.StatServiceImp;
import com.metasol.utils.EOResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/stats")
@AllArgsConstructor
public class RestStatisticController {

    private final StatServiceImp statService;

    @GetMapping("/products/{id}")
    EOResponse<?> StateByProductId(@PathVariable(name = "id") Long id) {
        StatResponseDto message = statService.statisBydProductId(id);
        return EOResponse.build(message);
    }

    @GetMapping("/customer/{id}")
    EOResponse<?> statMoneyTotalQuantityByCustomer(@PathVariable(name = "id") Long id) {
        StateCustomerOrderResponseDto message = statService.statMoneyTotalQuantityByCustomer(id);
        return EOResponse.build(message);
    }

}
