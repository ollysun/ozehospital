package com.example.demo.services;

import com.example.demo.dao.FindProfileByDate;
import com.example.demo.dao.PatientProfileRequest;
import com.example.demo.entity.Patient;
import com.example.demo.exception.HospitalException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static javax.validation.constraints.Pattern.*;

@Slf4j
@AllArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;

    private static final Pattern UUID_REGEX_PATTERN =
            compile("^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$");

//    public static final String UUID_PATTERN = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";
    public List<Patient> getAllPatient(UUID staffId) {

        if(!isValidUUID(staffId)){
            throw new HospitalException("Invalid Staff UUID");
        }
        if(!staffRepository.findByStaffUuid(staffId).isPresent()){
            throw new ResourceNotFoundException("Wrong Staff on UUID :: " + staffId);
        }
        return patientRepository.findPatientWithTwoYearRegistration();
    }

    public void writePatientProfileIntoCSV(PatientProfileRequest patientProfileRequest, Writer write) {
        if(!isValidUUID(patientProfileRequest.getStaffId())){
            throw new HospitalException("Invalid Staff UUID");
        }
        if(staffRepository.findByStaffUuid(patientProfileRequest.getStaffId()).isPresent()){
            Patient patient = patientRepository.findById(patientProfileRequest.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found on :: " + patientProfileRequest.getPatientId()));
            writeToCsv(patient, write);
        }else{
            throw new ResourceNotFoundException("Staff not found on :: " + patientProfileRequest.getPatientId());
        }
    }

    private void writeToCsv(Patient patient, Writer writer){
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                csvPrinter.printRecord(patient.getId(),
                        patient.getAge(), patient.getName(),
                        patient.getLastVisitDate());
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
            throw new HospitalException("Error While writing CSV " + e.getMessage());
        }
    }

    public void deleteMultipleProfileByDate(UUID staffId, String fromDate, String toDate) {
        if(!isValidUUID(staffId)){
            throw new HospitalException("wrong UUID");
        }

        if(staffRepository.findByStaffUuid(staffId).isPresent()) {
            List<Patient> patientList = patientRepository.findByLastVisitDateBetween(LocalDate.parse(fromDate),
                    LocalDate.parse(toDate));
            patientRepository.deleteAll(patientList);
        }else{
            throw new ResourceNotFoundException("Staff not found on :: " + staffId);
        }
    }

    private static boolean isValidUUID( UUID str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str.toString()).matches();
    }


    }
