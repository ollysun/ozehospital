package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "staff_uuid",unique = true,nullable = false)
    private UUID staffUuid;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "updated_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate updatedDate;

}
