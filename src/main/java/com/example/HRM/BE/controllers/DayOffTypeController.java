package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import com.example.HRM.BE.services.DayOffTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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

    @Secured("ROLE_ADMIN")
    @PostMapping
    public DayOffTypeEntity addNewDayOffType(@RequestBody @Valid DayOffType dayOffType) {
        return dayOffTypeService.addNewDayOffType(dayOffType);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public DayOffTypeEntity updateDayOffType(@PathParam("id") int id,
                                             @RequestBody @Validated DayOffType dayOffType) {
        dayOffType.setId(id);
        return dayOffTypeService.updateDayOffType(id, dayOffType);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteDayOffType(@PathVariable("id") int id) {
        dayOffTypeService.deleteDayOffType(id);
    }
}
