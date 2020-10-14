package com.example.HRM.BE.converters.DayOffTypeConverter;

import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import com.example.HRM.BE.repositories.DayOffTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DayOffTypeToDayOffTypeEntity extends Converter<DayOffType, DayOffTypeEntity> {

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

    @Override
    public DayOffTypeEntity convert(DayOffType source) {

        DayOffTypeEntity dayOffTypeEntity = new DayOffTypeEntity();

        dayOffTypeEntity.setId(source.getId());
        dayOffTypeEntity.setName(source.getName());

        return dayOffTypeEntity;
    }
}
