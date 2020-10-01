package com.example.HRM.BE.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    private String name;

    @OneToMany(mappedBy = "categoryEntities", cascade = CascadeType.ALL)
    private List<SkillEntity> skillEntities;

    public CategoryEntity() { }

    public CategoryEntity(int id, String name) {
        this.id = id;
        this.name =  name;
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
}
