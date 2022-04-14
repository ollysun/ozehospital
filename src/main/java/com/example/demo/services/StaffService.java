package com.example.demo.services;

import com.example.demo.dao.StaffRequest;
import com.example.demo.dao.UpdateStaffRequest;
import com.example.demo.entity.Staff;
import com.example.demo.exception.HospitalException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@Slf4j
@AllArgsConstructor
@Service
public class StaffService {

    private final StaffRepository staffRepository;

//    public static final String UUID_PATTERN = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";
//    private static final Pattern UUID_REGEX_PATTERN =
//            Pattern.compile("^[{]?[0-9a-fA-F]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9a-fA-F]{12}[}]?$");

    public Staff createStaff(StaffRequest staffRequest){
        try{
            Staff st = new Staff();
            st.setName(staffRequest.getName());
            st.setRegistrationDate(staffRequest.getRegisterDate());
            st.setStaffUuid(UUID.randomUUID());
            return staffRepository.save(st);
        }catch (Exception ex){
            log.debug("error happen" + ex.getMessage());
            throw new HospitalException(ex.getMessage());
        }
    }

    public Staff updateStaff(UpdateStaffRequest updateStaffRequest){

        Staff staff = staffRepository.findByStaffUuid(updateStaffRequest.getStaffUUid())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found on :: " + updateStaffRequest.getStaffUUid()));

        staff.setName(updateStaffRequest.getName());
        staff.setUpdatedDate(LocalDate.now());

        return staff;
    }

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

}
