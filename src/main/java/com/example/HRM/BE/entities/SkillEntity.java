package com.example.HRM.BE.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "skills")
public class SkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    private String name;

    @ManyToOne
    @JoinColumn
    private CategoryEntity categoryEntities;

    @ManyToMany(mappedBy = "skillEntities")
    private Set<UserEntity> userEntities;

    public SkillEntity() { }

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

    public CategoryEntity getCategoryEntity() {
        return categoryEntities;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntities = categoryEntity;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
