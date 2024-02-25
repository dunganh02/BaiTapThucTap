package com.metasol.repositories;

import com.metasol.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsEntityRepository extends JpaRepository<NewsEntity, Long> {
}
