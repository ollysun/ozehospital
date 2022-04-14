package com.example.demo.dao;

import com.example.demo.annotation.ValidUuid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UpdateStaffRequest {
    @NotBlank(message = "Please the name of the Staff")
    private String name;

    @ValidUuid
    @NotNull(message = "Staff UUID cannot be blank")
    private UUID staffUUid;
}
