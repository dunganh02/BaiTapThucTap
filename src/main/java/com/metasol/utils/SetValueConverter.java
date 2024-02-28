package com.metasol.utils;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.OrderDetailRequestDto;
import com.metasol.dto.request.OrderRequestDto;
import com.metasol.entity.OrderDetailEntity;
import com.metasol.entity.OrderEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.ICustomerRepository;
import com.metasol.repositories.IPriceRepository;
import com.metasol.repositories.IProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Anh Dũng
 * 27/02/2024
 * Dùng để conver và set giá trị từ dto vào entity
 */
@Component
@AllArgsConstructor
public class SetValueConverter {
    private final IPriceRepository priceRepos;
    private final ICustomerRepository customerRepos;
    private final IProductRepository productRepos;

    /**
     * @param orderRequestDto: chứa order{listSP}
     * @param order:           entity
     *                         Set giá trị của orderRequestDto -> OrderEntity
     */
    @Transactional
    public void setOrderDtoValue(OrderRequestDto orderRequestDto,
                                 OrderEntity order) {
        double totalMoney = 0;
        int quantity = 0;

        order.setCustomer(orderRequestDto.getCustomer());
        // chứa List OrderDetailRequestDto -> duyệt từng item trong OrderDetailRequestDto để set Value vào entity -> Lưu orderDetailList
        List<OrderDetailRequestDto> orderDetailRequestDtoList = orderRequestDto.getOrderDetailsDtoList();

        //Chứa List OrderDetailEntity để chuẩn bị lưu
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        Long customer_id = orderRequestDto.getCustomer().getId();

        // set từ dto->entity
        for (OrderDetailRequestDto orderDetailDto : orderDetailRequestDtoList) {

            OrderDetailEntity orderDetail = new OrderDetailEntity();  // Tạo đối tượng mới cho mỗi vòng lặp
            setOrderDetailDtoValue(orderDetail, orderDetailDto, order, customer_id);
            totalMoney += orderDetail.getPrice() * orderDetailDto.getNumberOfProduct();// so tien cua 1 product
            quantity += orderDetailDto.getNumberOfProduct();

            orderDetailList.add(orderDetail);
        }
        order.setOrderDetails(orderDetailList);
        order.setTotalMoney(totalMoney);
        order.setQuantitySold(quantity);

    }

    /**
     * @param orderDetail    : entity orderDetail
     * @param orderDetailDto : request dto
     * @param order          : entity order
     * @param customerId     : id customer
     *                       Set giá trị vào OrderDetailEntity khi nhận được OrderDetailRequestDto
     */
    public void setOrderDetailDtoValue(OrderDetailEntity orderDetail,
                                        OrderDetailRequestDto orderDetailDto,
                                        OrderEntity order,
                                        Long customerId) {

        try {
            if (productRepos.existsById(orderDetailDto.getProducts().getId())) {

                Long product_id = orderDetailDto.getProducts().getId();
                Long type_id = customerRepos.findTypeByOrderIdAndCustomerId(customerId);
                Double price = priceRepos.findPriceByProductIdAndTypeId(product_id, type_id);

                orderDetail.setOrder(order);
                orderDetail.setProduct(orderDetailDto.getProducts());
                orderDetail.setNumberOfProduct(orderDetailDto.getNumberOfProduct());
                orderDetail.setPrice(price);
            }
        } catch (Exception e) {
            throw new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND);
        }
    }

}
