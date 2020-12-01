package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollRepository extends JpaRepository<PayrollEntity, Integer> {

    PayrollEntity findByDatePayrollDetailEntityUserEntityId(int id);

}
