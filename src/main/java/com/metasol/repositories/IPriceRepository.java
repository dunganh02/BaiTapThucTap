package com.metasol.repositories;

import com.metasol.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPriceRepository extends JpaRepository<PriceEntity, Long> {
}
