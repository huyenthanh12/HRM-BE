package com.example.HRM.BE.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "date_payroll_detail")
@NoArgsConstructor
@AllArgsConstructor
public class DatePayrollDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private Date timeCheckIn;

    @Column(nullable = false)
    private Date timeCheckOut;

    @ManyToOne
    @JoinColumn UserEntity userEntity;
}
