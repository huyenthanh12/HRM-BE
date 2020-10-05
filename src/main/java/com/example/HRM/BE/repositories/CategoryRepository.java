package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query(
            value = "SELECT * from categories as c where c.name like CONCAT('%', :keyword, '%') ORDER BY id",
            nativeQuery = true
    )
    List<CategoryEntity> findAllByKeyword(@Param("keyword") String keyword);

    Optional<CategoryEntity> findByName(String name);
}
