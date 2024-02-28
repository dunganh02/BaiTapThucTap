package com.metasol.repositories;

import com.metasol.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriceRepository extends JpaRepository<PriceEntity, Long> {

//    @Query("SELECT p.price FROM Price p " +
//            "JOIN Product pr ON p.product.id = pr.id " +
//            "JOIN Type t ON p.type.id = t.id " +
//            "WHERE pr.id = :productId AND t.id = :typeId"))
//    Float findByTypeIdAndProductId(
//            @Param("typeId") Long typeId,
//            @Param("productId") Long productId
//    );

    @Query("SELECT pr.price " +
            "FROM PriceEntity pr " +
            "WHERE pr.type.id = :typeId AND pr.product.id = :productId")
    Double findPriceByProductIdAndTypeId(@Param("productId") Long productId, @Param("typeId") Long typeId);

}
