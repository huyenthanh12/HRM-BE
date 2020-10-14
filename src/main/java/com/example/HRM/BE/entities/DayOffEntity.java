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

    @Column(nullable = false)
    @NonNull
    private Date dayStart;

    @Column(nullable = false)
    @NonNull
    private Date dayEnd;

    @Column(nullable = false)
    @NonNull
    private Date createAt;

    @Column(nullable = false)
    @NonNull
    private String description;

    @Column(nullable = false)
    @NonNull
    private String status;

    @ManyToOne
    @JoinColumn
    private DayOffTypeEntity dayOffTypeEntity;

    @ManyToOne
    @JoinColumn UserEntity userEntity;
}
