package com.metasol.services.Imp;

import com.metasol.constant.ErrorCode;
import com.metasol.constant.MessageCode;
import com.metasol.dto.request.OrderDetailRequestDto;
import com.metasol.dto.request.OrderRequestDto;
import com.metasol.dto.response.OrderDetailResponseDto;
import com.metasol.dto.response.OrderResponseDto;
import com.metasol.entity.OrderDetailEntity;
import com.metasol.entity.OrderEntity;
import com.metasol.entity.ProductEntity;
import com.metasol.exception.EOException;
import com.metasol.repositories.ICustomerRepository;
import com.metasol.repositories.IOrderRepository;
import com.metasol.repositories.IPriceRepository;
import com.metasol.repositories.IProductRepository;
import com.metasol.services.IOrderService;
import com.metasol.utils.SetValueConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImp implements IOrderService {

    private final IOrderRepository orderRepos;
    private final IProductRepository productRepos;
    private final SetValueConverter setValueConverter;
    private final ICustomerRepository customerRepos;
    private final IPriceRepository priceRepos;


    @Override
    @Transactional
    public OrderResponseDto create(OrderRequestDto dto) {
        OrderEntity order = new OrderEntity();
        setValueConverter.setOrderDtoValue(dto, order);
        orderRepos.save(order);
        return new OrderResponseDto(order);
    }

    @Override
    public OrderResponseDto create2(OrderRequestDto dto) {

        OrderEntity order = new OrderEntity();
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();

        order.setCustomer(dto.getCustomer());
        Long type_id = customerRepos.findTypeByOrderIdAndCustomerId(dto.getCustomer().getId());
        Double total = Double.valueOf(0);
        int quantitySold = 0;

        // xu ly order detail
        for (OrderDetailRequestDto orderDetailReqDto : dto.getOrderDetailsDtoList()) {

            OrderDetailEntity orderDetail = new OrderDetailEntity();

            // Tim san pham trong db -> null thi bao loi
            Long product_id = orderDetailReqDto.getProducts().getId();
            Optional<ProductEntity> isProduct = productRepos.findById(product_id);
            if (isProduct.isPresent()) {
                orderDetail.setProduct(orderDetailReqDto.getProducts());
            } else {
                throw new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND);
            }

            orderDetail.setNumberOfProduct(orderDetailReqDto.getNumberOfProduct());
            // Tim gia san pham xong set
            Double price = priceRepos.findPriceByProductIdAndTypeId(product_id, type_id);
            orderDetail.setPrice(price);
            orderDetailEntityList.add(orderDetail);

            // số lượng sản phẩm && tổng số tiền
            quantitySold += orderDetailReqDto.getNumberOfProduct();
            total += orderDetailReqDto.getNumberOfProduct() + price;
        }

        order.setOrderDetails(orderDetailEntityList);
        order.setTotalMoney(total);
        order.setQuantitySold(quantitySold);

        orderRepos.save(order);
        return new OrderResponseDto(order);
    }

    @Override
    public OrderResponseDto update(Long id, OrderRequestDto dto) {
        OrderEntity entity = orderRepos.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        setValueConverter.setOrderDtoValue(dto, entity);
        orderRepos.save(entity);

        return new OrderResponseDto(entity);
    }

    @Override
    public void delete(Long id) {
        orderRepos.deleteById(id);
    }

    @Override
    public OrderResponseDto getById(Long id) {
        OrderEntity orderResponse = orderRepos.findById(id)
                .orElseThrow(() -> new EOException(ErrorCode.ENTITY_NOT_FOUND, MessageCode.ENTITY_NOT_FOUND));
        return new OrderResponseDto(orderResponse);
    }

    @Override
    public Page<OrderResponseDto> getAll(PageRequest pageRequest) {
        Page<OrderEntity> orderList = orderRepos.findAll(pageRequest);

        return orderList.map(this::convertToResponse);
    }

    /**
     * @param entity :OrderDetailEntity
     * @return : dto - OrderDetail
     * Convert từ OrderDetailEntity -> OrderDetailResponseDto
     */
    private OrderDetailResponseDto convertToListResponse(OrderDetailEntity entity) {
        OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();

        orderDetailResponseDto.setProductId(entity.getProduct().getId());
        orderDetailResponseDto.setPrice(entity.getPrice());
        orderDetailResponseDto.setNumberOfProduct(entity.getNumberOfProduct());
        orderDetailResponseDto.setOderId(entity.getOrder().getId());
        return orderDetailResponseDto;
    }

    /**
     * @param entity: OrderEntity
     * @return : dto Order
     * Convert từ OrderEntity -> OrderResponseDto
     */
    private OrderResponseDto convertToResponse(OrderEntity entity) {
        OrderResponseDto responseDto = new OrderResponseDto();

        List<OrderDetailEntity> orderDetailEntityList = entity.getOrderDetails();
        List<OrderDetailResponseDto> orderDetailResponseDtos = new ArrayList<>();

        // convertOrderDetail
        for (OrderDetailEntity oDEntity : orderDetailEntityList) {
            OrderDetailResponseDto orderDetailRespDto = convertToListResponse(oDEntity);
            orderDetailResponseDtos.add(orderDetailRespDto);
        }

        responseDto.setOrderDetailList(orderDetailResponseDtos);
        responseDto.setTotal_money(entity.getTotalMoney());
        responseDto.setQuantity_sold(entity.getQuantitySold());
        return responseDto;
    }


}
