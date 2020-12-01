package com.example.HRM.BE.converters.DatePayrollDetail;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.ProfileConverter.ProfileToUserEntity;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatePayrollDetailToDatePayrollDetailEntity extends Converter<DatePayrollDetail, DatePayrollDetailEntity> {

    @Autowired
    private ProfileToUserEntity profileToUserEntity;

    @Override
    public DatePayrollDetailEntity convert(DatePayrollDetail source) {

        DatePayrollDetailEntity datePayrollDetailEntity = new DatePayrollDetailEntity();

        datePayrollDetailEntity.setId(source.getId());
        datePayrollDetailEntity.setTimeCheckIn(source.getTimeCheckIn());
        datePayrollDetailEntity.setTimeCheckOut(source.getTimeCheckOut());
        datePayrollDetailEntity.setUserEntity(profileToUserEntity.convert(source.getUser()));

        return datePayrollDetailEntity;
    }
}
