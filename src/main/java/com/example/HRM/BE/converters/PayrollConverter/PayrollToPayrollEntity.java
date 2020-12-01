package com.example.HRM.BE.converters.PayrollConverter;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.DTO.Payroll;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.DatePayrollDetail.DatePayrollDetailToDatePayrollDetailEntity;
import com.example.HRM.BE.converters.ProfileConverter.ProfileToUserEntity;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import com.example.HRM.BE.entities.PayrollEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayrollToPayrollEntity extends Converter<Payroll, PayrollEntity> {

    @Autowired
    private ProfileToUserEntity profileToUserEntity;

    @Autowired
    private DatePayrollDetailToDatePayrollDetailEntity datePayrollDetailToDatePayrollDetailEntity;

    @Override
    public PayrollEntity convert(Payroll source) {

        PayrollEntity payrollEntity = new PayrollEntity();

        payrollEntity.setId(source.getId());
        payrollEntity.setHourlyWages(source.getHourlyWages());
        payrollEntity.setSalary(source.getSalary());
        payrollEntity.setWorkingHours(source.getWorkingHours());

        List<DatePayrollDetail> datePayrollDetailList = source.getPayrollDateDetail();
        List<DatePayrollDetailEntity> datePayrollDetailEntityList = new ArrayList<>();

        for (DatePayrollDetail datePayrollDetail : datePayrollDetailList) {
            DatePayrollDetailEntity datePayrollDetailEntity = datePayrollDetailToDatePayrollDetailEntity.convert(datePayrollDetail);
            datePayrollDetailEntityList.add(datePayrollDetailEntity);
        }

        payrollEntity.setDatePayrollDetailEntity(datePayrollDetailEntityList);

        return payrollEntity;
    }
}
