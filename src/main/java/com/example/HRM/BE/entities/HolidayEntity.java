package com.example.HRM.BE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "holidays")
@NoArgsConstructor
@RequiredArgsConstructor
public class HolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @NonNull
    private Date dateStart;

    @Column(nullable = false)
    @NonNull
    private Date dateEnd;

    @Column(nullable = false)
    @NonNull
    private String reason;
}
