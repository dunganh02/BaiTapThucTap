package com.metasol.repositories;

import com.metasol.dto.response.StatResponseDto;
import com.metasol.entity.OrderDetailEntity;
import com.metasol.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    Boolean existsByProductId(Long id);

    @Query("select (count(o) > 0) from OrderEntity o where o.customer.id = :id")
    Boolean existsByCustomer(Long id);

    @Query("select p.id as product_id, " +
            "p.name as product_name, " +
            "SUM(o.numberOfProduct) as total_quantity_sold " +
            "from ProductEntity  p " +
            "inner join OrderDetailEntity o on o.product.id = :id  " +
            "GROUP BY p.id, p.name")
    List<Object[]> quantityByProductId(Long id);


    @Query("SELECT c.id, c.name, count(o), sum(o.totalMoney)" +
            " from OrderEntity o" +
            " inner join CustomerEntity c ON :id is null or o.customer.id = :id")
    List<Object[]> statMoneyTotalQuantityByOrder(Long id);

}
