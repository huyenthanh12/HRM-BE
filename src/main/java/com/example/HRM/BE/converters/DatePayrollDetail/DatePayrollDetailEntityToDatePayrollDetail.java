package com.example.HRM.BE.converters.DatePayrollDetail;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.ProfileConverter.UserEntityToProfile;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatePayrollDetailEntityToDatePayrollDetail extends Converter<DatePayrollDetailEntity, DatePayrollDetail> {

    @Autowired
    private UserEntityToProfile userEntityToProfile;

    @Override
    public DatePayrollDetail convert(DatePayrollDetailEntity source) {

        DatePayrollDetail datePayrollDetail = new DatePayrollDetail();

        datePayrollDetail.setId(source.getId());
        datePayrollDetail.setTimeCheckIn(source.getTimeCheckIn());
        datePayrollDetail.setTimeCheckOut(source.getTimeCheckOut());
        datePayrollDetail.setUser(userEntityToProfile.convert(source.getUserEntity()));

        return datePayrollDetail;
    }
}
