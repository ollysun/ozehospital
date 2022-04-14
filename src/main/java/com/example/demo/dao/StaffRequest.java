package com.example.demo.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
public class StaffRequest {
    @NotBlank(message = "Please the name of the Staff")
    private String name;


    @NotNull(message = "Registration date cannot be blank")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerDate;
}
