package com.example.HRM.BE.converters.DayOffTypeConverter;

import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class DayOffTypeEntityToDayOffType extends Converter<DayOffTypeEntity, DayOffType> {

    @Override
    public DayOffType convert(DayOffTypeEntity source) {

        DayOffType dayOffType = new DayOffType();

        dayOffType.setId(source.getId());
        dayOffType.setName(source.getName());

        return dayOffType;
    }
}
