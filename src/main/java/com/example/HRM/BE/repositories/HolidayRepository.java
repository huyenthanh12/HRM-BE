package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {

    @Query(
            value = "SELECT * FROM holidays where date_start::::text like CONCAT('%', ?1, '%')",
            nativeQuery = true
    )
    List<HolidayEntity> findHolidayByYear(int year);
}
