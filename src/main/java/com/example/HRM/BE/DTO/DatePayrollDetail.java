package com.example.HRM.BE.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DatePayrollDetail {

    private int id;

    @NotNull
    private Profile user;

    private Date timeCheckIn;

    private Date timeCheckOut;
    
}
