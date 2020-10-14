package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.DayOff;
import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import com.example.HRM.BE.exceptions.DayOffException.DayOffNotFound;
import com.example.HRM.BE.repositories.DayOffTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayOffTypeService {

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

    @Autowired
    private Converter<DayOffTypeEntity, DayOffType> dayOffTypeEntityDayOffTypeConverter;

    public List<DayOffType> getAllDayOffTypes() {
        return dayOffTypeEntityDayOffTypeConverter.convert(dayOffTypeRepository.findAll());
    }

    public DayOffType getDayOffTypeFollowID(int id) {
        Optional<DayOffTypeEntity> dayOffTypeEntityOptional = dayOffTypeRepository.findById(id);

        if (!dayOffTypeEntityOptional.isPresent()) {
            throw new DayOffNotFound();
        } else {
            return dayOffTypeEntityDayOffTypeConverter.convert(dayOffTypeEntityOptional.get());
        }
    }
}
