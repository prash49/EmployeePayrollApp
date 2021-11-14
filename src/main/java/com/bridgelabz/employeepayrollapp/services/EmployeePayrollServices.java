package com.bridgelabz.employeepayrollapp.services;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.exceptions.EmployeePayrollException;
import com.bridgelabz.employeepayrollapp.model.EmployeePayrollData;
import com.bridgelabz.employeepayrollapp.repository.EmployeePayrollRepository;
import com.bridgelabz.employeepayrollapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;

/**
 * Purpose: Service Layer To Perform Implementations
 *
 * @author :Prashanth.N
 * @version 1.0
 * @since 11-11-2021
 */
@Service
@Slf4j
public class EmployeePayrollServices implements IEmployeePayrollService {

    @Autowired
    private EmployeePayrollRepository employeePayrollRepository;

    @Autowired
    TokenUtil tokenUtil;

    /**
     * Purpose: finding all Employees Payroll Data Service is Written in this method
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public List<EmployeePayrollData> getEmployeePayrollData() {

        return employeePayrollRepository.findAll();
    }

    /**
     * Purpose: Service for Getting EmployeePayroll Data By Employee Id is written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public EmployeePayrollData getEmployeePayrollDataById(int empId) {
        return employeePayrollRepository.findById(empId)
                .orElseThrow(() -> new EmployeePayrollException("Employee with EmployeeId" + empId
                        + " Doesn't Exists...!"));

    }

    /**
     * Purpose: Service for Creating New Employee PayrollData is written in this method
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public EmployeePayrollData createEmployeePayrollData(EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData empData = null;
        empData = new EmployeePayrollData(empPayrollDTO);
        log.debug("Employee Data: " + empData.toString());
        return employeePayrollRepository.save(empData);
    }

    /**
     * Purpose: Service for Updating Employee Payroll Data by Id is written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public EmployeePayrollData updateEmployeePayrollData(int empId, EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData empData = this.getEmployeePayrollDataById(empId);
        empData.updateEmployeePayollData(empPayrollDTO);
        return employeePayrollRepository.save(empData);
    }

    /**
     * Purpose: Service for Deleting Employee Payroll Data by Id is written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public void deleteEmployeePayrollData(int empId) {
        EmployeePayrollData empData = this.getEmployeePayrollDataById(empId);
        employeePayrollRepository.delete(empData);

    }

    /**
     * Purpose: Service for Getting The list of  Employee Payroll Data for Given Department written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public List<EmployeePayrollData> getEmployeesPayrollDataByDepartment(String department) {
        return employeePayrollRepository.findEmployeesByDepartment(department);
    }

    /**
     * Purpose: Service for Getting the list  Employee Payroll Data for given gender  is written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public List<EmployeePayrollData> getEmployeesPayrollDataByGender(String gender) {
        return employeePayrollRepository.findEmployeesByGender(gender);
    }

    /**
     * Purpose: Service for Deleting All the Employee Payroll Data  written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public String deleteAllEmployeePayrollData() {
        employeePayrollRepository.deleteAll();
        return "All Data Delete";
    }

    @Override
    public List<EmployeePayrollData> getEmployeesPayrollDataByName(String name) {

        return employeePayrollRepository.findByNames(name);
    }

    /**
     * Purpose: Service for Getting List of All Employee Payroll Data by Proving JWT(Token) is written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
    @Override
    public List<EmployeePayrollData> getAllEmployeePayrollData(String token) {
        Long id = tokenUtil.decodeToken(token);
        Optional<EmployeePayrollData> empData = employeePayrollRepository.findById(Math.toIntExact((id)));
        if (empData.isPresent()) {
            List<EmployeePayrollData> employeePayrollDataList = employeePayrollRepository.findAll();
            return employeePayrollDataList;
        }
        return null;
    }

    /**
     * Purpose: Service for Getting Employee Payroll Data providing Token is written
     *
     * @author :Prashanth.N
     * @version 1.0
     * @since 11-11-2021
     */
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


}
