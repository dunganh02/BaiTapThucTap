package com.metasol.repositories;

import com.metasol.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select (count(p) > 0) from ProductEntity p where p.id = :id")
    boolean existsById(Long id);

    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
    Optional<ProductEntity> getDetailProduct(@Param("productId") Long productId);

}
