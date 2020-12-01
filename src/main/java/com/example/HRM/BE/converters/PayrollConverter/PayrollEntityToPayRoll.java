package com.example.HRM.BE.converters.PayrollConverter;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.DTO.Payroll;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.DatePayrollDetail.DatePayrollDetailEntityToDatePayrollDetail;
import com.example.HRM.BE.converters.ProfileConverter.UserEntityToProfile;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import com.example.HRM.BE.entities.PayrollEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayrollEntityToPayRoll extends Converter<PayrollEntity, Payroll> {

    @Autowired
    private UserEntityToProfile userEntityToProfile;

    @Autowired
    private DatePayrollDetailEntityToDatePayrollDetail datePayrollDetailEntityToDatePayrollDetail;

    @Override
    public Payroll convert(PayrollEntity source) {

        Payroll payroll = new Payroll();

        payroll.setId(source.getId());
        payroll.setHourlyWages(source.getHourlyWages());
        payroll.setWorkingHours(source.getWorkingHours());
        payroll.setSalary(source.getSalary());

        List<DatePayrollDetailEntity> datePayrollDetailEntityList = source.getDatePayrollDetailEntity();
        List<DatePayrollDetail> datePayrollDetailList = new ArrayList<>();

        for (DatePayrollDetailEntity datePayrollDetailEntity : datePayrollDetailEntityList) {
            DatePayrollDetail datePayrollDetail = datePayrollDetailEntityToDatePayrollDetail.convert(datePayrollDetailEntity);
            datePayrollDetailEntityList.add(datePayrollDetailEntity);
        }

        payroll.setPayrollDateDetail(datePayrollDetailList);

        return payroll;
    }
}
