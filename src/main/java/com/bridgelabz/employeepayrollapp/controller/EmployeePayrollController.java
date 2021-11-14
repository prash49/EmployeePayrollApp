package com.bridgelabz.employeepayrollapp.controller;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.dto.ResponseDTO;
import com.bridgelabz.employeepayrollapp.exceptions.EmployeePayrollException;
import com.bridgelabz.employeepayrollapp.model.EmployeePayrollData;
import com.bridgelabz.employeepayrollapp.repository.EmployeePayrollRepository;
import com.bridgelabz.employeepayrollapp.services.IEmployeePayrollService;
import com.bridgelabz.employeepayrollapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Purpose: Controller For Employee Payroll Services
 *
 * @author :Prashanth.N
 * @version 1.0
 * @since 11-11-2021
 */
@RestController
@RequestMapping("/employeePayrollservice")
@Slf4j
public class EmployeePayrollController {

    @Autowired
    private IEmployeePayrollService employeePayrollService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    EmployeePayrollRepository repo;

    // Controller for API to get EmployeePayroll Data
    @RequestMapping(value = {"", "/", "/get"})
    public ResponseEntity<ResponseDTO> getEmployeePayrollData() {
        List<EmployeePayrollData> empDataList = null;
        empDataList = employeePayrollService.getEmployeePayrollData();
        ResponseDTO respDTO = new ResponseDTO("Get Call Success", empDataList);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    // Controller for API to get EmployeePayroll Data for specific ID
    @GetMapping("/get/{empId}")
    public ResponseEntity<ResponseDTO> getEmployeePayrollData(@PathVariable("empId") long empId) {
        EmployeePayrollData empData = null;
        empData = employeePayrollService.getEmployeePayrollDataById((int) empId);
        ResponseDTO respDTO = new ResponseDTO("Get call for ID Successful:", empData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    // Controller for API to Create New Employee Payroll Data
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> addEmployeePayrollData(
            @Valid @RequestBody EmployeePayrollDTO empPayrollDTO) {
        log.debug("Employee DTO" + empPayrollDTO.toString());
        EmployeePayrollData empData = null;
        empData = employeePayrollService.createEmployeePayrollData(empPayrollDTO);
        ResponseDTO respDTO = new ResponseDTO("Create Employee PayrollData:", tokenUtil.createToken(empData.getEmployeeId()));
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    // Controller for API to Update A Particular EmployeePayroll Data Using Employee ID
    @PutMapping("/update/{empId}")
    public ResponseEntity<ResponseDTO> updateEmployeePayrollData(@PathVariable("empId") int empId,
                                                                 @Valid @RequestBody EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData empData = null;
        empData = employeePayrollService.updateEmployeePayrollData(empId, empPayrollDTO);
        ResponseDTO respDTO = new ResponseDTO("Update Employee PayrollData Successful:", tokenUtil.createToken(empData.getEmployeeId()));
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);

    }

    // Controller for API to Delete Specific Employee PAyrollData by ID
    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<ResponseDTO> deleteEmployeePayrollData(@PathVariable("empId") int empId) {
        employeePayrollService.deleteEmployeePayrollData(empId);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successful,Deleted Id:", empId);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    // Controller for API to get List of Employee Payroll Data based on Department
    @GetMapping("/department/{department}")
    public ResponseEntity<ResponseDTO> getEmployeeByDepartment(@PathVariable String department) {

        List<EmployeePayrollData> employeeList = null;
        employeeList = employeePayrollService.getEmployeesPayrollDataByDepartment(department);
        ResponseDTO response = new ResponseDTO("Get Call for Department Successful", employeeList);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    // Controller for API to Get List of Employee Payroll Data by Gender
    @GetMapping("/gender/{gender}")
    public ResponseEntity<ResponseDTO> getEmployeeByGender(@PathVariable String gender) {
        List<EmployeePayrollData> employeeList = null;
        employeeList = employeePayrollService.getEmployeesPayrollDataByGender(gender);
        ResponseDTO response = new ResponseDTO("Get Call for gender Successful", employeeList);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    // Controller for API to Reading The All Data in Employee PAyroll Database By giving Token as input
    @GetMapping("/readdata")
    public ResponseEntity<ResponseDTO> readdata(@RequestHeader(name = "token") String token) throws EmployeePayrollException {
        List<EmployeePayrollData> employeeList = null;
        employeeList = employeePayrollService.getAllEmployeePayrollData(token);
        if (employeeList.size() > 0) {
            ResponseDTO responseDTO = new ResponseDTO("all user Fetched successfully", employeeList);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        } else {
            throw new EmployeePayrollException("No Data Found");
        }

    }

    // Controller for API to Read the Specific Token Data
    @GetMapping("/readupdatedata")
    public ResponseEntity<ResponseDTO> readupdatedata(@RequestHeader(name = "token") String token) throws EmployeePayrollException {
        Optional<EmployeePayrollData> employeeList = null;
        employeeList = employeePayrollService.getupdateEmployeePayrollData(token);

        ResponseDTO responseDTO = new ResponseDTO("Updated data", employeeList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

    }

    // Controller for API to Delete All the Employees Payroll Data
    @DeleteMapping("/deleteall")
    public ResponseEntity<ResponseDTO> deleteAllEmployeePayrollData() {
        String empData = employeePayrollService.deleteAllEmployeePayrollData();
        ResponseDTO respDTO = new ResponseDTO("Deleted Successful,Deleted Id:", empData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseDTO> getEmployeeByName(@PathVariable String name) {

        List<EmployeePayrollData> employeeList = null;
        employeeList = employeePayrollService.getEmployeesPayrollDataByName(name);
        ResponseDTO response = new ResponseDTO("Get Call for Department Successful", employeeList);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

// using just JPA repository finding list of contacts by name;
    @GetMapping("/findbyname/{name}")
    public ResponseEntity<ResponseDTO> findingContactByName(@PathVariable String name) {
        List<EmployeePayrollData> data = repo.findByName(name);
        ResponseDTO responseDTO = new ResponseDTO("Find By name Data", data);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/findbygender/{gender}")
    public ResponseEntity<ResponseDTO> findingContactByGender(@PathVariable String gender) {
        List<EmployeePayrollData> data = repo.findByGender(gender);
        ResponseDTO responseDTO = new ResponseDTO("Find By Gender", data);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }


}

