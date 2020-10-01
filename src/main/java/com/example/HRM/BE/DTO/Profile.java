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

    @NotNull
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

    private boolean enable;

    @AllArgsConstructor
    @Data
    @Builder
    public static class Role {
        private String name;
    }
//    public Profile() {
//
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Date getStartingDate() {
//        return startingDate;
//    }
//
//    public void setStartingDate(Date startingDate) {
//        this.startingDate = startingDate;
//    }
//
//    public int getContractPeriod() {
//        return contractPeriod;
//    }
//
//    public void setContractPeriod(int contractPeriod) {
//        this.contractPeriod = contractPeriod;
//    }
//
//    public String getAvatarBase() {
//        return avatarBase;
//    }
//
//    public void setAvatarBase(String avatarBase) {
//        this.avatarBase = avatarBase;
//    }
//
//    public List<Skill> getSkills() {
//        return skills;
//    }
//
//    public void setSkills(List<Skill> skills) {
//        this.skills = skills;
//    }
//
//    public boolean isEnable() {
//        return enable;
//    }
//
//    public void setEnable(boolean enable) {
//        this.enable = enable;
//    }
}
