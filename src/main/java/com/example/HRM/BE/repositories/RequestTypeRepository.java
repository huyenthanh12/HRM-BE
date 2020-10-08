package com.example.HRM.BE.repositories;

import com.example.HRM.BE.DTO.RequestType;
import com.example.HRM.BE.entities.RequestTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestTypeRepository extends JpaRepository<RequestTypeEntity, Integer> {

    Optional<RequestTypeEntity> findByName(String name);

    List<RequestTypeEntity> findByOrderByNameAsc();

    @Query(
            value = "SELECT * from request_types as c where c.name like CONCAT('%', :keyword, '%') ORDER BY id",
            nativeQuery = true
    )
    List<RequestTypeEntity> findByKeyword(@Param("keyword") String keyword);
}
