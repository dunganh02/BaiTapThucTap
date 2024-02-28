package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.response.StatResponseDto;
import com.metasol.dto.response.StateCustomerOrderResponseDto;
import com.metasol.exception.EOException;
import com.metasol.repositories.IOrderDetailRepository;
import com.metasol.services.IStatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Anh Dũng
 * Date: 27/0/2024
 * Service xử lý thống kê
 */
@Service
@AllArgsConstructor
public class StatServiceImp implements IStatService {
    private final IOrderDetailRepository orderDetailRepos;


    // Caculate total product ...
    @Override
    public StatResponseDto statisBydProductId(Long productId) {
        boolean existProduct = orderDetailRepos.existsByProductId(productId);
        StatResponseDto respos = new StatResponseDto();

        if (existProduct) {
            List<Object[]> responseList = orderDetailRepos.quantityByProductId(productId);

            if (!responseList.isEmpty()) {
                Object[] responseArray = responseList.get(0);
                // Giả sử các giá trị trả về là theo thứ tự: product_id, product_name, total_quantity_sold
                respos.setId((Long) responseArray[0]);
                respos.setName((String) responseArray[1]);
                respos.setTotal_quantity_sold(Math.toIntExact((Long) responseArray[2]));
            }
        } else {
            throw new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND);
        }
        return respos;
    }

    @Override
    public StateCustomerOrderResponseDto statMoneyTotalQuantityByCustomer(Long id) {
        if (!orderDetailRepos.existsByCustomer(id)) {
            throw new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND);
        }
        List<Object[]> responseList = orderDetailRepos.statMoneyTotalQuantityByOrder(id);

        return responseList.stream()
                .findFirst()
                .map(responseArray -> {
                    // Giả sử các giá trị trả về là theo thứ tự: product_id, product_name, total_quantity_sold
                    return new StateCustomerOrderResponseDto(
                            (Long) responseArray[0],
                            (String) responseArray[1],
                            (Integer) responseArray[2],
                            (Double) responseArray[3]
                    );
                })
                // nếu không có dữ liệu thì sẽ tạo ra 1 StateCustomerOrderResponseDto() mặc định
                .orElse(new StateCustomerOrderResponseDto());
    }


}
