package com.metasol.services;

import com.metasol.dto.response.StatResponseDto;
import com.metasol.dto.response.StateCustomerOrderResponseDto;


public interface IStatService {
    // thống kê số lượng order của 1 sản pham
    StatResponseDto statisBydProductId(Long productId);

    StateCustomerOrderResponseDto statMoneyTotalQuantityByCustomer(Long id);


}
