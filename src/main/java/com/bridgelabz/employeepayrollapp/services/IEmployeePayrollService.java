package com.bridgelabz.employeepayrollapp.services;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.model.EmployeePayrollData;

import java.util.List;
import java.util.Optional;

public interface IEmployeePayrollService {
    List<EmployeePayrollData> getEmployeePayrollData();

    EmployeePayrollData getEmployeePayrollDataById(int empId);

    EmployeePayrollData createEmployeePayrollData(EmployeePayrollDTO empPayrollDTO);

    EmployeePayrollData updateEmployeePayrollData(int empId, EmployeePayrollDTO empPayrollDTO);

    void deleteEmployeePayrollData(int empId);

    List<EmployeePayrollData> getEmployeesPayrollDataByDepartment(String department);

    List<EmployeePayrollData> getEmployeesPayrollDataByGender(String gender);


    List<EmployeePayrollData> getAllEmployeePayrollData(String token);

    Optional<EmployeePayrollData> getupdateEmployeePayrollData(String token);

    String deleteAllEmployeePayrollData();

    List<EmployeePayrollData> getEmployeesPayrollDataByName(String name);
}
