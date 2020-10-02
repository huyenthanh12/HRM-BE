package com.example.HRM.BE.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Profile {

    @NotNull
    private int id = 0;

    @NotBlank
    private String email;

    @NotEmpty
    @NotBlank
    private String firstName;

    @NotEmpty
    @NotBlank
    private String lastName;

    @NotNull
    private int age;

    private Date birthday;

    @NotNull
    private String sex;

    @NotEmpty
    @NotBlank
    private String address;

    private String phone;

    private Date startingDate;

    private int contractPeriod;

    private String avatarBase;

    private List<Skill> skills;

    private List<Role> roles;

    private boolean disable;

    @AllArgsConstructor
    @Data
    @Builder
    public static class Role {
        private String name;
    }
}
