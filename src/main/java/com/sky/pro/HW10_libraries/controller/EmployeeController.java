package com.sky.pro.HW10_libraries.controller;


import com.sky.pro.HW10_libraries.employee.Employee;
import com.sky.pro.HW10_libraries.exception.IsAllLowerCaseException;
import com.sky.pro.HW10_libraries.service.EmployeeService;
import com.sky.pro.HW10_libraries.exception.IsAllLowerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sky.pro.HW10_libraries.exception.FullMapException;
import com.sky.pro.HW10_libraries.exception.IsAlfaException;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(

            EmployeeService employeeService) {

        this.employeeService = employeeService;

    }

    @GetMapping("/all")

    public String getEmployees() {
        return "<pre><h2><b>Список всех сотрудников:</b></h2>\n" + "" + employeeService.printAllEmployees() + "<pre>";
    }

    @GetMapping("/add")
    public String addEmployee(@RequestParam("fullName") String fullName,
                              @RequestParam("salary") int salary,
                              @RequestParam("dept") int dept) throws IsAlfaException, IsAllLowerCaseException {
        if (employeeService.getEmployees().containsKey(fullName)) {
            return "такой сотрудник уже есть";
        }
        try {
            employeeService.addEmployee(fullName, salary, dept);
        } catch (FullMapException e) {
            throw new RuntimeException("\u001B[31m Нельзя добавить сотрудника, " +
                    "достигнут искуственный предел заполнения интерфейса Map\u001B[0m");
        } catch (RuntimeException e) {
            throw  new IsAlfaException( " " );


        } catch (Exception e) {
            throw new IsAllLowerCaseException ("\u001B[31m ФИО пишутся с большой буквы  \u001B[0m");
        }
        return "Сотрудник: " + employeeService.getEmployees().get(fullName) + " добавлен";
    }

    @GetMapping("/find")
    public String findEmployee(@RequestParam("fullName") String fullName) {
        if (employeeService.getEmployees().containsKey(fullName)) {
            return "Сотрудник: " + employeeService.getEmployees().get(fullName);
        } else {
            return "Сотрудник не найден";
        }
    }

    @GetMapping("/remove")
    public String removeEmployee(@RequestParam("fullName") String fullName) throws IsAlfaException, IsAllLowerCaseException, IsAllLowerException {
        try {
            employeeService. removeEmployee(fullName);
        } catch (NullPointerException e) {
            throw new RuntimeException("\u001B[31m Такого сотрудника не существует \u001B[0m");
        } catch (RuntimeException e) {
            throw new IsAlfaException("\u001B[31m ФИО могут содержать только буквы  \u001B[0m");
        } catch (Exception e) {
            throw new IsAllLowerCaseException("\u001B[31m ФИО пишутся с большой буквы  \u001B[0m");
        }
        return "Сотрудник " + fullName + " удален";
    }


    @GetMapping("/change")
    public String changeEmployee(@RequestParam("fullNameDeletingEmployee") String fullNameDeletingEmployee,
                                 @RequestParam("fullNameNewEmployee") String fullNameNewEmployee,
                                 @RequestParam("newSalary") int newSalary,
                                 @RequestParam("newDept") int newDept) throws IsAlfaException, IsAllLowerException, IsAllLowerCaseException, FullMapException {
        try {
            employeeService.changeEmployee(fullNameDeletingEmployee,
                    fullNameNewEmployee,
                    newSalary,
                    newDept);
        } catch (NullPointerException e) {
            throw new RuntimeException("\u001B[31m Такого сотрудника не существует \u001B[0m");
        }
        return "Сотрудник " + fullNameDeletingEmployee + " заменен сотрудником " + fullNameNewEmployee;
    }

    @GetMapping("/printAllEmployeesWithoutDept")
    public String printAllEmployeesWithoutDept() {
        return "<pre><h2><b>Список всех сотрудников без отделов:</b></h2>\n" +
                "" + employeeService.printEmployeesWithoutDepartment() + "<pre>";
    }

    @GetMapping("/salaryIndexing")
    public String salaryIndexing(@RequestParam("percent") double percentOfIndexing) {
        StringBuilder sb = new StringBuilder(" ");
        String previous_salary= employeeService.salariesBILO().toString();
        employeeService.salaryIndexing(percentOfIndexing);
        return "<pre><h2><b>Зарплаты БЫЛО:</b></h2>\n" + "" + previous_salary + "<pre>" +
                "<pre><h2><b>Зарплаты СТАЛО:</b></h2>\n" + "" + employeeService.printAllEmployees() + "<pre>" +
                "<pre><h2>Размер индексации - " + percentOfIndexing + "%</h2></pre>";
    }

    @GetMapping("/salaryLess")
    public String salaryLessThan(@RequestParam("lessThan") int salaryLessThan) {
        return "<pre><h2><b>Сотрудник с зарплатой меньше чем - " + salaryLessThan + " рублей</b></h2><pre>" +
                "<pre>" + employeeService.salaryLessThan(salaryLessThan) + "<pre>";

    }

    @GetMapping("/salaryMore")
    public String salaryMoreThan(@RequestParam("moreThan") int salaryMoreThan) {
        return "<pre><h2><b>Сотрудник с зарплатой меньше чем - " + salaryMoreThan + " рублей</b></h2><pre>" +
                "<pre>" +employeeService.salaryMoreThan(salaryMoreThan) + "<pre>";

    }

    @GetMapping("/findEmployeesMinMaxSalary")
    public String findEmployeesMinMaxSalary() {
        return "<pre><h2><b>Сотрудник с минимальной зарплатой - " +
                employeeService.findEmployeesMinimalSalary() + " " +
                employeeService.getEmployees().get(employeeService.findEmployeesMinimalSalary()).getSalary() +
                " р. </b></h2><pre>" + "<pre><h2><b>Сотрудник с максимальной зарплатой - " +
                employeeService.findEmployeesMaximalSalary() + " " +
                employeeService.getEmployees().get(employeeService.findEmployeesMaximalSalary()).getSalary() +
                " р.</b></h2><pre>";

    }

    @GetMapping("/findAndPrintEmployeeById")
    public String findAndPrintEmployeeById(@RequestParam("id") int id) {
        int idRez;
        try {
            idRez = Integer.valueOf(employeeService.getEmployees().get(employeeService.findAndPrintEmployeeById(id)).getId());
        } catch (NullPointerException e) {
            throw new RuntimeException("\u001B[31m Сотрудника с таким ID не существует \u001B[0m");
        }
        return "<pre><h2><b>Сотрудник чей id=" + idRez +
                " - " + employeeService.findAndPrintEmployeeById(id) + "</b></h2><pre>";

    }

    @GetMapping("/monthMiddleSalary")
    public String monthMiddleSalary() {
        return "<pre><h2><b>Среднемесячная зарплата - " +
                employeeService.monthMiddleSalary(employeeService.monthSumSalary()) +
                " рублей</b></h2><pre>";

    }

    @GetMapping("/middleSalaryByDept")
    public String middleSalaryByDept(@RequestParam("dept") int dept) {
        String stringRez;
        try {
            stringRez =employeeService.middleSalaryByDepartment(dept);
        } catch (RuntimeException e) {
            throw new RuntimeException("\u001B[31m Номера департаментов 1 - 5, введите корректный номер \u001B[0m");
        }
        return "<pre><h2><b>Средняя зарплата по отделу - " +
                stringRez +
                " рублей</b></h2><pre>";

    }

    @GetMapping("/max-salary")
    public String getMaxSalaryByDept(@RequestParam("departmentId") int dept) {
        String stringRez = employeeService.getMaxSalaryByDepartment(dept).toString();
        return "<pre><h2><b>Максимальная зарплата по отделу: " +
                stringRez +
                " рублей</b></h2><pre>";

    }

    @GetMapping("/min-salary")
    public String getMinSalaryByDept(@RequestParam("departmentId") int dept) {
        Employee stringRez = employeeService.getMinSalaryByDepartment(dept);
        return "<pre><h2><b>Минимальная зарплата по отделу: " +
                stringRez +
                " рублей</b></h2><pre>";

    }

    @GetMapping(value = "/departments/allEmployeesInDept", params = "departmentId")
    public String findAllEmployeesAccordingDept(@RequestParam("departmentId") int dept) {
        String stringRez = employeeService.findAllEmployeesAccordingDepartment(dept);
        return "<pre><h2><b>Сотрудники " + dept + "-го" + " отдела:\n" +
                stringRez + "</b></h2><pre>";

    }

    @GetMapping("/departments/all")
    public String printEmployeesAccordingToDept() {
        return "<pre><h2><b>Список всех сотрудников по отделам:</b></h2>\n" +
                "" + employeeService.printEmployeesAccordingToDepartments()  + "<pre>";
    }

}
