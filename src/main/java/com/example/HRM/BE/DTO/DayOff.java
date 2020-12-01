package com.example.HRM.BE.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class DayOff {

    private int id;

    private Date dayStart;

    private Date dayEnd;

    private Date createAt;

    private String description;

    DayOffType dayOffType;

    private String status;

    private Profile user;
}
