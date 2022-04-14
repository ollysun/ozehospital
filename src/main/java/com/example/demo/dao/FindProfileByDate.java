package com.example.demo.dao;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
public class FindProfileByDate {
    @NotBlank
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")
    private String staffId;

    @NotNull
    @DateTimeFormat(pattern="yyy-MM-dd")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern="yyy-MM-dd")
    private Date endDate;
}
