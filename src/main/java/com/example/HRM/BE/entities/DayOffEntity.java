package com.example.HRM.BE.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "day_off")
@NoArgsConstructor
@RequiredArgsConstructor
public class DayOffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonAlias("day_start")
    @Column(nullable = false)
    @NonNull
    private Date dayStart;

    @JsonAlias("day_end")
    @Column(nullable = false)
    @NonNull
    private Date dayEnd;

    @JsonAlias("create_at")
    @Column(nullable = false)
    private Date createAt;

    @JsonAlias("description")
    @Column(nullable = false)
    @NonNull
    private String description;

    @JsonAlias("status")
    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn
    private DayOffTypeEntity dayOffTypeEntity;

    @ManyToOne
    @JoinColumn UserEntity userEntity;
}
