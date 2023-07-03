
package com.sky.pro.HW10_libraries.service;


import com.sky.pro.HW10_libraries.employee.Employee;

import com.sky.pro.HW10_libraries.exception.*;

import com.sky.pro.HW10_libraries.exception.IsAllLowerCaseException;


import java.util.List;

import java.util.Map;


public interface EmployeeService {

    StringBuilder printAllEmployees();





    void addEmployee(String fullName, int salary, int department) throws FullMapException, IsAlfaException,   IsAllLowerCaseException;


    void removeEmployee(String fullName) throws IsAlfaException, IsAllLowerException;


    Map<String, Employee> getEmployees();


    void changeEmployee(String fullNameDeletingEmployee,

                        String fullNameNewEmployee,

                        Integer newSalary,

                        Integer newDepartment) throws IsAlfaException, IsAllLowerException, IsAllLowerCaseException, FullMapException;


    String printEmployeesWithoutDepartment();


    Map<Integer, List<Employee>> printEmployeesAccordingToDepartments();


    StringBuilder printEmployeesWithoutDept();


    Map <Integer, List<Employee>> printEmployeesAccordingToDeptartments();


    void salaryIndexing(double percentOfIndexing);



    StringBuilder salariesBILO();


    StringBuilder salaryLessThan(int lessThanThisNum);


    StringBuilder salaryMoreThan(int moreThanThisNum);


    String findEmployeesMinimalSalary();


    String findEmployeesMaximalSalary();





    int monthSumSalary();



    String monthMiddleSalary(int sum);


    String middleSalaryByDepartment(int deptOfEmployee);


    Employee getMaxSalaryByDepartment(int deptOfEmployee);


    Employee getMinSalaryByDepartment(int deptOfEmployee);


    String findAllEmployeesAccordingDepartment(int deptOfEmployee);



}
