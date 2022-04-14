package com.example.demo.controller;

import com.example.demo.controller.StaffController;
import com.example.demo.dao.StaffRequest;
import com.example.demo.entity.Staff;
import com.example.demo.repository.PatientRepository;
import com.example.demo.services.PatientService;
import com.example.demo.services.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(StaffController.class)
public class StaffControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private PatientService patientService;

    @MockBean
    private StaffService staffService;

    @Test
    public void createStaff_success() throws Exception {

        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setName("mike");
        LocalDate DATETest = LocalDate.of(2011, 8, 25);
        staffRequest.setRegisterDate(DATETest);
        Staff staff  = new Staff();
        staff.setName(staffRequest.getName());
        staff.setRegistrationDate(staffRequest.getRegisterDate());
        staff.setStaffUuid(UUID.randomUUID());

        doReturn(staff).when(staffService).createStaff(any());

        mockMvc.perform(post("/api/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(staffRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("mike")));
    }
}
