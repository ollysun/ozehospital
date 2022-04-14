package com.example.demo.controller;

import com.example.demo.dao.StaffRequest;
import com.example.demo.dao.UpdateStaffRequest;
import com.example.demo.entity.Staff;
import com.example.demo.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody @Valid StaffRequest staffRequest) {
        Staff staff = staffService.createStaff(staffRequest);
        return new ResponseEntity<>(staff, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Staff> updateStaff(@RequestBody @Valid UpdateStaffRequest staffRequest){
        Staff staff = staffService.updateStaff(staffRequest);
        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

}
