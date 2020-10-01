package com.example.HRM.BE.common;

public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    public static final String PENDING="PENDING";
    public static final String APPROVED="APPROVED";
    public static final String REJECTED="REJECTED";
}
