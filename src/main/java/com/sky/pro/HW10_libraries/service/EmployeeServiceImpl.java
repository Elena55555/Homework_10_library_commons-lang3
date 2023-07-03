
package com.sky.pro.HW10_libraries.service;

import com.sky.pro.HW10_libraries.employee.Employee;

import com.sky.pro.HW10_libraries.exception.IsAllLowerException;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import com.sky.pro.HW10_libraries.exception.FullMapException;

import com.sky.pro.HW10_libraries.exception.IsAlfaException;

import com.sky.pro.HW10_libraries.exception.IsAllLowerCaseException ;

import java.text.DecimalFormat;

import java.util.*;

import java.util.stream.Collectors;

@Service

public class EmployeeServiceImpl implements EmployeeService {    public Map<String, Employee> employees = new HashMap<>(Map.of(
            "1",

            new Employee("Иван Иванов",
                    1000000,
                    1
            ),
            "2",

            new Employee("Петр Петров",
                    10000060,
                    2
            ),
            "3",

            new Employee("Василий Васильев",
                    1785876869,
                    3
            ),
            "4",

            new Employee("Виктор Викторов",
                    7678125,
                    3
            ),
            "5",

            new Employee(" Ира Годунова",
                    8765589,
                    4
            )

    ));
    String[] departments = {
            "IT",
            "руководящий",
            "юридический",
            "отдел закупок",
            "финансовый",
    };
    Map<String, Integer> departmentsCodes = Map.of(
            "IT", 1,
            "руководящий", 1,
            "юридический", 2,
            "отдел закупок", 3,
            "финансовый", 4
    );
    @Override
    public StringBuilder printAllEmployees() {
        StringBuilder rezString = new StringBuilder("");
        if (employees.isEmpty()) {
            return rezString.append(" Department не содержит ни одного сотрудника");
        }
        employees.values().stream()
                .forEach(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDepartment()  + "\n"));
        return rezString;
    }
    @Override
    public void addEmployee(String fullName, int salary, int department ) throws FullMapException, IsAlfaException,   IsAllLowerCaseException {
        if (Employee.getIdCounter() > 20) {
            throw new FullMapException();
        }
        if(!StringUtils.isAlphaSpace(fullName)){
            throw new IsAlfaException( " " );
        }
        if(StringUtils.isAllLowerCase(fullName)){
            throw new IsAllLowerCaseException( " ");
        }
        employees.put(fullName, new Employee(fullName, salary, department ));
    }
    @Override
    public void removeEmployee(String fullName) throws  IsAlfaException, IsAllLowerException {
        if(!StringUtils.isAlphaSpace(fullName)){
            throw new IsAlfaException(" "  );
        }
        if(!StringUtils.isAllLowerCase(fullName)){
            throw new IsAllLowerException(" "  );
        }
        if (employees.containsKey(fullName)) {
            employees.remove(fullName);
        } else {
            throw new RuntimeException();
        }
    }
    @Override
    public Map<String, Employee> getEmployees() {
        return employees;
    }
    @Override
    public void changeEmployee(String fullNameDeletingEmployee,
                               String fullNameNewEmployee,
                               Integer newSalary,
                               Integer newDepartment) throws IsAlfaException, IsAllLowerException, IsAllLowerCaseException, FullMapException {

        if (employees.containsKey(fullNameDeletingEmployee)) {
            removeEmployee(fullNameDeletingEmployee);
            addEmployee(fullNameNewEmployee, newSalary, newDepartment);
        } else {
            throw new NullPointerException();
        }
    }
    @Override
    public String printEmployeesWithoutDepartment() {
        return null;
    }
    @Override
    public Map<Integer, List<Employee>> printEmployeesAccordingToDepartments() {
        return null;
    }
    @Override
    public StringBuilder printEmployeesWithoutDept() {
        StringBuilder rezString = new StringBuilder("");
        if (employees.isEmpty()) {
            return rezString.append("Map не содержит ни одного сотрудника");
        }
        employees.values().stream()
                .forEach(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        "\n"));
        return rezString;
    }
    @Override
    public Map <Integer, List<Employee>> printEmployeesAccordingToDeptartments() {
        Map<Integer, List<Employee>> employeeInDepartment = employees.values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment  ));
        return employeeInDepartment;
    }

    @Override
    public void salaryIndexing(double percentOfIndexing) {
        employees.values().stream().forEach(employee -> {
            double increasedSalary = employee.getSalary() * (1 + percentOfIndexing / 100);
            employee.setSalary((int) increasedSalary);
        });
    }
     @Override
    public StringBuilder salariesBILO() {
        Map<String, Employee> emloyeeSalaryesBILO = employees;
        StringBuilder rezString = new StringBuilder("");
        if (emloyeeSalaryesBILO.isEmpty()) {
            return rezString.append("интерфейс Map не содержит ни одного сотрудника");
        }
        employees.values().stream()
                .forEach(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDepartment() + "\n"));
        return rezString;
    }

    @Override
    public StringBuilder salaryLessThan(int lessThanThisNum) {
        int lessSalariesCounter;
        StringBuilder rezString = new StringBuilder("");
        lessSalariesCounter = (int) employees.values().stream()
                .filter(employee -> employee.getSalary() < lessThanThisNum)
                .peek(employee -> {
                    rezString.append("- " + employee.getFullName() +
                            ", зарплата: " + employee.getSalary() +
                            ", отдел: " + employee.getDepartment() + "\n");
                }).count();
        if (lessSalariesCounter == 0) {
            return rezString.append("Сотрудников с зарплатой ниже " + lessThanThisNum + " рублей " + " - нет");
        }
        return rezString;
    }

    @Override
    public StringBuilder salaryMoreThan(int moreThanThisNum) {
        int moreSalariesCounter;
        StringBuilder rezString = new StringBuilder("");
        moreSalariesCounter = (int) employees.values().stream()
                .filter(employee -> employee.getSalary() > moreThanThisNum)
                .peek(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDepartment() + "\n"))
                .count();
        if (moreSalariesCounter == 0) {
            return rezString.append("Сотрудников с зарплатой выше " + moreThanThisNum + " рублей " + " - нет");
        }
        return rezString;
    }
    @Override
    public String findEmployeesMinimalSalary() {
        return employees.values().stream()
                .min(Comparator.comparingInt(employee -> employee.getSalary()))
                .map(employee -> employee.getFullName()).orElse("");
    }
    @Override
    public String findEmployeesMaximalSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(e -> e.getSalary()))
                .map(employee -> employee.getFullName()).orElse("");
    }

    @Override
    public int monthSumSalary() {
        int sum = 0;
        for (Employee employee : employees.values()) {
            sum = employee.getSalary() + sum;
        }
        return sum;
    }
    @Override
    public String monthMiddleSalary(int sum) {
        return new DecimalFormat("###,###.##").format((double) sum / employees.size());
    }
    @Override
    public String middleSalaryByDepartment(int departmentOfEmployee) {
        if (departmentOfEmployee >= 6 && departmentOfEmployee < 1) {
            throw new RuntimeException();
        }
        int sumSalaries = 0;
        int deptsCounter = 0;
        for (Employee employee : employees.values()) {
            if (employee.getDepartment() == departmentOfEmployee) {
                sumSalaries = employee.getSalary() + sumSalaries;
                deptsCounter++;
            }
        }
        return Double.toString(sumSalaries / deptsCounter);
    }
    @Override
    public Employee getMaxSalaryByDepartment(int departmentOfEmployee) {
        if (departmentOfEmployee >= 6 && departmentOfEmployee < 1) {
            throw new RuntimeException();
        }
        return employees.values().stream()
                .filter(e -> e.getDepartment() == departmentOfEmployee)
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }
    @Override
    public Employee getMinSalaryByDepartment(int departmentOfEmployee) {
        if (departmentOfEmployee >= 6 && departmentOfEmployee < 1) {
            throw new RuntimeException();
        }
        return employees.values().stream()
                .filter(e -> e.getDepartment() ==departmentOfEmployee)
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }
    @Override
    public String findAllEmployeesAccordingDepartment(int departmentOfEmployee) {
        if (departmentOfEmployee >= 5 && departmentOfEmployee < 1) {
            throw new RuntimeException();
        }
        return employees.values().stream()
                .filter(e -> e.getDepartment() == departmentOfEmployee)
                .map(e -> e + "\n")
                .collect(Collectors.toList())
                .toString();
    }
}







