package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM Patient t WHERE DATEDIFF(year,t.last_visit_date,CURRENT_DATE) <= 2", nativeQuery = true)
    List<Patient> findPatientWithTwoYearRegistration();

    List<Patient> findByLastVisitDateBetween(LocalDate startDate, LocalDate endDate);

}
