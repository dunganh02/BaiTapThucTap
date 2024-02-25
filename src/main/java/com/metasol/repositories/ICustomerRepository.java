package com.metasol.repositories;

import com.metasol.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}
