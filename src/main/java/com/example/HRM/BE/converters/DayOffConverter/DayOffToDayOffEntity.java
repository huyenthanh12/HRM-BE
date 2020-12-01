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
public class DayOffToDayOffEntity extends Converter<DayOff, DayOffEntity> {

    @Autowired
    private Converter<Profile, UserEntity> profileUserEntityConverter;

    @Autowired
    private Converter<DayOffType, DayOffTypeEntity> dayOffTypeDayOffTypeEntityConverter;

    @Override
    public DayOffEntity convert(DayOff source) {

        DayOffEntity dayOffEntity = new DayOffEntity();

        dayOffEntity.setId(source.getId());
        dayOffEntity.setDayStart(source.getDayStart());
        dayOffEntity.setDayEnd(source.getDayEnd());
        dayOffEntity.setCreateAt(source.getCreateAt());
        dayOffEntity.setDescription(source.getDescription());
        dayOffEntity.setStatus(source.getStatus());
        dayOffEntity.setDayOffTypeEntity(dayOffTypeDayOffTypeEntityConverter.convert(source.getDayOffType()));
        dayOffEntity.setUserEntity(profileUserEntityConverter.convert(source.getUser()));

        return dayOffEntity;
    }
}
