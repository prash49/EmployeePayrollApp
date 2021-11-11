package com.bridgelabz.employeepayrollapp.services;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.exceptions.EmployeePayrollException;
import com.bridgelabz.employeepayrollapp.model.EmployeePayrollData;
import com.bridgelabz.employeepayrollapp.repository.EmployeePayrollRepository;
import com.bridgelabz.employeepayrollapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeePayrollServices implements IEmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository employeePayrollRepository;

    @Autowired
    TokenUtil tokenUtil;


    @Override
    public List<EmployeePayrollData> getEmployeePayrollData() {

        return employeePayrollRepository.findAll();
    }

    @Override
    public EmployeePayrollData getEmployeePayrollDataById(int empId) {
        return employeePayrollRepository.findById(empId)
                .orElseThrow(() -> new EmployeePayrollException("Employee with EmployeeId" + empId
                        + " Doesn't Exists...!"));

    }

    @Override
    public EmployeePayrollData createEmployeePayrollData(EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData empData = null;
        empData = new EmployeePayrollData(empPayrollDTO);
        log.debug("Employee Data: " + empData.toString());
        return employeePayrollRepository.save(empData);
    }

    @Override
    public EmployeePayrollData updateEmployeePayrollData(int empId, EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData empData = this.getEmployeePayrollDataById(empId);
        empData.updateEmployeePayollData(empPayrollDTO);
        return employeePayrollRepository.save(empData);
    }

    @Override
    public void deleteEmployeePayrollData(int empId) {
        EmployeePayrollData empData = this.getEmployeePayrollDataById(empId);
        employeePayrollRepository.delete(empData);

    }

    @Override
    public List<EmployeePayrollData> getEmployeesPayrollDataByDepartment(String department) {
        return employeePayrollRepository.findEmployeesByDepartment(department);
    }

    @Override
    public List<EmployeePayrollData> getEmployeesPayrollDataByGender(String gender) {
        return employeePayrollRepository.findEmployeesByGender(gender);
    }

    @Override
    public String deleteallEmployeePayrollData() {
        employeePayrollRepository.deleteAll();
        return "All Data Delete";
    }

    @Override
    public List<EmployeePayrollData> getAllEmployeePayrollData(String token) {
        Long id = tokenUtil.decodeToken(token);
        Optional<EmployeePayrollData> empData = employeePayrollRepository.findById(Math.toIntExact(id));
        if (empData.isPresent()) {
            List<EmployeePayrollData> employeePayrollDataList = employeePayrollRepository.findAll();
            return employeePayrollDataList;
        }
        return null;
    }

    @Override
    public Optional<EmployeePayrollData> getupdateEmployeePayrollData(String token) {
        Long id = tokenUtil.decodeToken(token);
        Optional<EmployeePayrollData> empData = employeePayrollRepository.findById(Math.toIntExact(id));
        if (empData.isPresent()) {
            Optional<EmployeePayrollData> employeePayrollUpdateData = employeePayrollRepository.findById(Math.toIntExact(id));
            return employeePayrollUpdateData;
        }
        return null;
    }

    @Override
    public String deleteAllEmployeePayrollData() {
        employeePayrollRepository.deleteAll();
        return "All Data Delete";
    }

}
