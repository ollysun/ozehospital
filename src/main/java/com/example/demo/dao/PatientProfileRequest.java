package com.example.demo.dao;

import com.example.demo.annotation.ValidUuid;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
public class PatientProfileRequest {
    @ValidUuid
    @NotNull(message = "Please enter the staff uuid")
    private UUID staffId;

    @NotNull
    private Integer patientId;
}
