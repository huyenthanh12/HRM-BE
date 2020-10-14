package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.DayOffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOffEntity, Integer> {

    List<DayOffEntity> findByOrderByStatusAsc();
}
