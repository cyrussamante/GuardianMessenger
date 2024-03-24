package com.example.guardianmessenger.employees;

public class EmployeeInfo {

    public String name;
    public String email;
    private int age;
    public String position;
    public String department;
    private int salary;

    public EmployeeInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public EmployeeInfo(String name, String email, int age, String position, String department, int salary) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.position = position;
        this.department = department;
        this.salary = salary;
    }

}
