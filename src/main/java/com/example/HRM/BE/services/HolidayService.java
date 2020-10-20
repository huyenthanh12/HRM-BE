package com.example.HRM.BE.services;

import com.example.HRM.BE.common.CommonMethods;
import com.example.HRM.BE.entities.HolidayEntity;
import com.example.HRM.BE.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HolidayService {

    @Autowired
    private CommonMethods commonMethods;

    @Autowired
    private HolidayRepository holidayRepository;

    public List<Date> getHolidayThisYear(int year) {

        List<HolidayEntity> holidayEntityList = holidayRepository.findHolidayByYear(year);
        List<Date> result = new ArrayList<>();

        for (HolidayEntity holidayEntity : holidayEntityList) {
            result.addAll(commonMethods.getListDateBetweenTwoDates(holidayEntity.getDateStart(), holidayEntity.getDateStart()));
        }

        return result;
    }
}
