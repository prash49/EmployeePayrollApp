package com.bridgelabz.employeepayrollapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@ToString
public class EmployeePayrollDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Employee Name is invalid")
    public String name;

    @Min(value = 500, message = "Min Wage should be more than 500")
    public long salary;
    @Pattern(regexp = "male|female", message = " Gender  needs to be male or female")
    public String gender;

//    @JsonFormat(pattern = "dd MMM yyyy")
//    @NotNull(message = "Startdate should not be empty")
//    @PastOrPresent(message = "startDate should be past or todays date")
//    public LocalDate startDate;
    @NotBlank(message = "Note can not be empty")
    public String note;
    @NotBlank(message = "profilePic can not be empty")
    public String profilePic;
    @NotNull(message = "department should not be empty")
    public List<String> departments;


}
