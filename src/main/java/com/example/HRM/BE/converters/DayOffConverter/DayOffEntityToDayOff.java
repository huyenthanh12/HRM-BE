package com.example.HRM.BE.converters.DayOffConverter;

import com.example.HRM.BE.DTO.DayOff;
import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffEntity;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import com.example.HRM.BE.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DayOffEntityToDayOff extends Converter<DayOffEntity, DayOff> {

    @Autowired
    private Converter<DayOffTypeEntity, DayOffType> dayOffTypeEntityDayOffTypeConverter;

    @Autowired
    private Converter<UserEntity, Profile> userEntityProfileConverter;

    @Override
    public DayOff convert(DayOffEntity source) {

        DayOff dayOff = new DayOff();

        dayOff.setId(source.getId());
        dayOff.setDayStart(source.getDayStart());
        dayOff.setDayEnd(source.getDayEnd());
        dayOff.setCreateAt(source.getCreateAt());
        dayOff.setDescription(source.getDescription());
        dayOff.setDayOffType(dayOffTypeEntityDayOffTypeConverter.convert(source.getDayOffTypeEntity()));
        dayOff.setStatus(source.getStatus());
        dayOff.setUser(userEntityProfileConverter.convert(source.getUserEntity()));

        return dayOff;
    }

}
