package com.example.HRM.BE.repositories;

import com.example.HRM.BE.DTO.RequestType;
import com.example.HRM.BE.entities.RequestTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestTypeRepository extends JpaRepository<RequestTypeEntity, Integer> {

    Optional<RequestTypeEntity> findByName(String name);

    List<RequestTypeEntity> findByOrderByNameAsc();
}
