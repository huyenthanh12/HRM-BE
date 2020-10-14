package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.services.DayOffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/day_off_types")
public class DayOffTypeController {

    @Autowired
    private DayOffTypeService dayOffTypeService;

    @GetMapping
    public List<DayOffType> getAllDayOffTypes() {
        return dayOffTypeService.getAllDayOffTypes();
    }

    @GetMapping("/{id}")
    public DayOffType getDayOffTypeFollowID(@PathVariable("id") int id) {
        return dayOffTypeService.getDayOffTypeFollowID(id);
    }
}
