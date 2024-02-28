package com.metasol.repositories;

import com.metasol.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select customer.type.id " +
            "FROM CustomerEntity customer " +
            "WHERE  customer.id = :order_customer")
    Long findTypeByOrderIdAndCustomerId(@Param("order_customer") Long OrderCustomer);

    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}
