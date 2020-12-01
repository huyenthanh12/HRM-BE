package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.converters.DatePayrollDetail.DatePayrollDetailEntityToDatePayrollDetail;
import com.example.HRM.BE.repositories.DateDetailPayrollRepository;
import com.example.HRM.BE.services.DatePayrollDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll_date_detail")
public class DatePayrollDetailController {

    @Autowired
    DatePayrollDetailService datePayrollDetailService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<DatePayrollDetail> getAllDatesDetail() {
        return datePayrollDetailService.getAllDatesPayroll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public List<DatePayrollDetail> getDatesPayrollByUserID(@PathVariable("id") int id) {
        return datePayrollDetailService.getAllDatesPayrollByUserID(id);
    }

    @GetMapping("/me")
    public List<DatePayrollDetail> getMyDatesPayrollDetail() {
        return datePayrollDetailService.getMyDatesPayrollDetail();
    }

    @PostMapping
    public DatePayrollDetail addNewDatePayroll(DatePayrollDetail datePayrollDetail, @PathVariable("emailUser") String emailUser) {
        return datePayrollDetailService.addNewDatePayrollFollowUserEmail(datePayrollDetail, emailUser);
    }

}
