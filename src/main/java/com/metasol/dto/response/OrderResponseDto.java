package com.metasol.dto.response;

import com.metasol.entity.OrderEntity;
import lombok.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private List<OrderDetailResponseDto> orderDetailList;
    private int quantity_sold;
    private double total_money;

    public OrderResponseDto(OrderEntity order) {
        if (!ObjectUtils.isEmpty(order)) {
            this.setTotal_money(order.getTotalMoney());
            this.setQuantity_sold(order.getQuantitySold());
            this.orderDetailList = new LinkedList<>();
            /**
             * Nếu order.getOrderDetails() không rỗng -> thì tạo 1 stream từ danh sách  order.getOrderDetails()
             * Ánh xạ từng phần tử của danh sách thành thành đối tượng OrderDetailResponseDto
             * Sau đó lưu các đối tượng vào orderDetailList
             */
            if (!CollectionUtils.isEmpty(order.getOrderDetails())) {
                this.orderDetailList = order
                        .getOrderDetails()
                        .stream()
                        .map(OrderDetailResponseDto::new)
                        .collect(Collectors.toList());
            }
        }
    }
}
