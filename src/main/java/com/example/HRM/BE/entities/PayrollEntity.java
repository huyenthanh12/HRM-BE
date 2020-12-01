package com.example.HRM.BE.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "payrolls")
@NoArgsConstructor
@AllArgsConstructor
public class PayrollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @NotNull
    private double workingHours;

    @Column(nullable = false)
    @NotNull
    private double hourlyWages;
    
    @Column(nullable = false)
    private double salary;

    @OneToMany
    @Column(nullable = false)
    private List<DatePayrollDetailEntity> datePayrollDetailEntity;
}
