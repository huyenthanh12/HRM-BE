package com.example.HRM.BE.common;

import lombok.Data;

import java.util.List;

@Data
public class Email {

    private List<String> sendToEmail;

    private String subject;

    private String text;
}
