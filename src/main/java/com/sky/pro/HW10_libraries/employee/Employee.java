
package com.sky.pro.HW10_libraries.employee;



import java.util.Objects;

public class Employee {

    private final String fullName;

    private   int salary;

    private final int department;

    private final  int id;

    private static int idCounter = 0;



    public Employee(String fullName, int salary, int department) {

        this.fullName = fullName;

        this.salary = salary;

        this.department = department;

        this.id = id;

    }





    public String getFullName() {

        return fullName;

    }


    public int getSalary() {

        return salary;

    }


    public void setSalary(int salary) {

        this.salary = salary;

    }


    public int getDepartment() {

        return department;

    }


    public int getId() {

        return id;

    }



   public static int getIdCounter() {

        return idCounter;

    }


    public static void setIdCounter(int idCounter) {

        Employee.idCounter = idCounter;

    }


    @Override

    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return getSalary() == employee.getSalary() && getDepartment() == employee.getDepartment() && getId() == employee.getId() && Objects.equals(getFullName(), employee.getFullName());

    }


    @Override

    public int hashCode() {

        return Objects.hash(getFullName(), getSalary(), getDepartment(), getId());

    }


    @Override

    public String toString() {

        return "Employee{" +

                "fullName='" + fullName + '\'' +

                ", salary=" + salary +

                ", department=" + department +

                ", id=" + id +

                '}';

    }


}
