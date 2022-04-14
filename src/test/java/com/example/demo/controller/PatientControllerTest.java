package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.services.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private PatientService patientService;

    @Test
    void getPatients() throws Exception {
        LocalDate DATETest = LocalDate.of(2011, 8, 25);
        LocalDate DATETest2 = LocalDate.of(2012, 8, 25);

        Patient patient = new Patient();
        patient.setAge(20);
        patient.setName("mike");
        patient.setLastVisitDate(DATETest);

        Patient patient2 = new Patient();
        patient.setAge(31);
        patient.setName("mik2");
        patient.setLastVisitDate(DATETest2);

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);
        UUID uuid = UUID.randomUUID();
        when(patientService.getAllPatient(uuid))
                .thenReturn(patients);

        mockMvc.perform(get("/api/patient?staffUuid="+ uuid)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}