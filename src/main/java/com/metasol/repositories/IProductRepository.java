package com.metasol.repositories;

import com.metasol.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select (count(p) > 0) from ProductEntity p where p.id = :id")
    boolean existsById(Long id);

}
