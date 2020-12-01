package com.example.HRM.BE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "requests")
@NoArgsConstructor
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn
    private RequestTypeEntity requestTypeEntity;

    private String status;

    @ManyToOne
    @JoinColumn
    private UserEntity userEntity;

    private Date dateRequest;

    private Date createAt;

    private String reason;

    private String address;
}
