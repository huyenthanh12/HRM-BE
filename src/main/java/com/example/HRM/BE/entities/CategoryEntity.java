package com.example.HRM.BE.entities;

import com.example.HRM.BE.DTO.Skill;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    private String name;

    private String status;

    @OneToMany(mappedBy = "categoryEntities", cascade = CascadeType.ALL)
    private List<SkillEntity> skillEntities;

    public CategoryEntity() {    }

    public CategoryEntity(int id, String name, String status) {
        this.id = id;
        this.name =  name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SkillEntity> getSkillEntities() {
        return skillEntities;
    }

    public void setSkillEntities(List<SkillEntity> skillEntities) {
        this.skillEntities = skillEntities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
