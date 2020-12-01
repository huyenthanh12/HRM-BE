package com.example.HRM.BE.DTO;

import lombok.Data;

import java.util.List;

@Data
public class Payroll {

    private int id;

    private double salary;

    private double hourlyWages;

    private double workingHours;

    private List<DatePayrollDetail> payrollDateDetail;
}
