package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.DayOff;
import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffEntity;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import com.example.HRM.BE.exceptions.DayOffException.DayOffNotFound;
import com.example.HRM.BE.exceptions.DayOffTypeException.DayOffTypeHasExisted;
import com.example.HRM.BE.repositories.DayOffRepository;
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
    private DayOffRepository dayOffRepository;

    @Autowired
    private Converter<DayOffType, DayOffTypeEntity> dayOffTypeDayOffTypeEntityConverter;

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

    public DayOffTypeEntity addNewDayOffType(DayOffType dayOffType) {
        Optional<DayOffTypeEntity> dayOffTypeEntityOptional = dayOffTypeRepository.findByName(dayOffType.getName());

        if (dayOffTypeEntityOptional.isPresent()) {
            throw new DayOffTypeHasExisted();
        } else {
            return dayOffTypeRepository.save(dayOffTypeDayOffTypeEntityConverter.convert(dayOffType));
        }
    }

    public DayOffTypeEntity updateDayOffType(int id, DayOffType dayOffType) {

        Optional<DayOffTypeEntity> dayOffTypeEntityOptional = dayOffTypeRepository.findById(id);

        if (!dayOffTypeEntityOptional.isPresent()) {
            throw new DayOffNotFound();

        } else {
            Optional<DayOffTypeEntity> dayOffTypeEntity = dayOffTypeRepository.findByName(dayOffType.getName());
            if (dayOffTypeEntity.isPresent()) {
                throw new DayOffTypeHasExisted();
            }

            dayOffTypeEntityOptional.get().setName(dayOffType.getName());

            return dayOffTypeRepository.save(dayOffTypeDayOffTypeEntityConverter.convert(dayOffType));
        }
    }

    public void deleteDayOffType(int id) {

        Optional<DayOffTypeEntity> dayOffTypeEntityOptional = dayOffTypeRepository.findById(id);

        if (!dayOffTypeEntityOptional.isPresent()) {
            throw new DayOffNotFound();
        } else {
            //delete day off has day off type deleted
            for (DayOffEntity dayOffEntity : dayOffRepository.findByDayOffTypeEntityId(id)) {
                dayOffRepository.delete(dayOffEntity);
            }
        }
        dayOffTypeRepository.deleteById(id);
    }
}
