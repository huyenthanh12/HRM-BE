package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.DayOffTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DayOffTypeRepository extends JpaRepository<DayOffTypeEntity, Integer> {

    Optional<DayOffTypeEntity> findByName(String name);

    List<DayOffTypeEntity> findByOrderByNameAsc();
}
