package com.bridgelabz.employeepayrollapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class EmployeePayrollApp {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(EmployeePayrollApp.class, args);
       log.info("Employee Payroll App Started in {} Environment",
               context.getEnvironment().getProperty("environment"));
       log.info("Employee Payroll DB User is {}",
               context.getEnvironment().getProperty("spring.datasource.username"));
    }

}
