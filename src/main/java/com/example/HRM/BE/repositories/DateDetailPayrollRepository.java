package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DateDetailPayrollRepository extends JpaRepository<DatePayrollDetailEntity, Integer> {

    List<DatePayrollDetailEntity> findByUserEntityId (int id);

    List<DatePayrollDetailEntity> findByUserEntityEmail (String email);
}
