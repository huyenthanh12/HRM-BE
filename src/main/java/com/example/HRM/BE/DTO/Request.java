package com.example.HRM.BE.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Request {

    private int id;

    @NotNull
    private RequestType requestType;

    private String status;

    private Date dayRequest;

    private Date createAt;

    @NotBlank
    private String reason;

    private String address;

    private Profile user;
}
