package com.example.demo.controller;

import com.example.demo.dao.PatientProfileRequest;
import com.example.demo.entity.Patient;
import com.example.demo.services.PatientService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import java.time.LocalDate;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getPatients(
            @RequestParam(name = "staffUuid") UUID staffUuid){
        List<Patient> patientResponseList = patientService.getAllPatient(staffUuid);
        return new ResponseEntity<>(patientResponseList, HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<Void> downloadPatientProfileById(
            @RequestParam("staffUuid") @NotNull(message ="Staff UUID cannot be null") UUID staffUuid,
           @RequestParam("patientId") Integer patientId,
           HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"patient.csv\"");
        PatientProfileRequest patientProfileRequest = new PatientProfileRequest();
        patientProfileRequest.setPatientId(patientId);
        patientProfileRequest.setStaffId(staffUuid);
        patientService.writePatientProfileIntoCSV(patientProfileRequest, servletResponse.getWriter());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<String> deletePatient(@RequestParam("staffUuid") @NotNull(message ="end date cannot be null") UUID staffUuid,
                                         @RequestParam("from") @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                         @NotNull(message = "from date cannot be null") String fromDate,
                                         @RequestParam("end") @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                    @NotNull(message ="end date cannot be null") String endDate) throws ParseException {
        patientService.deleteMultipleProfileByDate(staffUuid, fromDate,endDate);
        return new ResponseEntity<>("Successful deletion ", HttpStatus.OK);

    }


}
