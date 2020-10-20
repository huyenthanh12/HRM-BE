package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.DayOff;
import com.example.HRM.BE.DTO.NumberDayOff;
import com.example.HRM.BE.entities.DayOffEntity;
import com.example.HRM.BE.services.DayOffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.HRM.BE.common.Constants.PERSONAL;

@RestController
@RequestMapping("/api/day_offs")
@Slf4j
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<DayOff> getAllDayOffs() {
        return dayOffService.getAllDaysOff();
    }

    @GetMapping("/me")
    public List<DayOff> getMyDayOffs() {
        return dayOffService.getMyDayOffs();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public DayOff getDayOffFollowID(@PathVariable("id") int id) {
        return dayOffService.getDayOffFollowID(id);
    }

    @GetMapping("/number_day_offs_by_user/{id}")
    public long getNumberDayOffsByUser(@PathVariable("id") int id,
                                      @RequestParam(value = "year", required = false) int year) {
        if (id == PERSONAL) {
            id = dayOffService.getUserID();
        }
        return dayOffService.getNumberDayOffsByUser(id, year);
    }

    @PostMapping
    public DayOffEntity requestNewDayOff(@RequestBody DayOff dayOff) {
        return dayOffService.requestNewDayOff(dayOff);
    }

    @DeleteMapping("/{id}")
    public void deleteDayOff(@PathVariable("id") int id) {
        dayOffService.deleteDayOff(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/accept/{id}")
    public DayOffEntity accept(@PathVariable("id") int id) {
        return dayOffService.acceptDayOff(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/rejected/{id}")
    public DayOffEntity rejectedDayOff(@PathVariable("id") int id) {
        return dayOffService.rejectDayOff(id);
    }
}
