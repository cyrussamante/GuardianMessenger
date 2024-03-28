package com.example.guardianmessenger.accounts;

import java.util.ArrayList;
import java.util.List;

public class EmployeeInfo {

    public String name;
    public String email;
    private int age;
    public String position;
    public String department;
    private int salary;

    private List<Employee> outreachApprovals;

    public void addOutreachApproval(Employee employee) {
        outreachApprovals.add(employee);
    }

    public void removeOutreachApproval(Employee employee) {
        outreachApprovals.remove(employee);
    }

    public EmployeeInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public EmployeeInfo(EmployeeInfo info) {
        this.name = String.valueOf(info.name);
        this.email = String.valueOf(info.email);
        this.age = info.age;
        this.position = info.position;
        this.department = info.department;
        this.salary = info.salary;
        this.outreachApprovals = info.outreachApprovals;
    }

    public EmployeeInfo(String name, String email, int age, String position, String department, int salary) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.outreachApprovals = new ArrayList<>();
    }

}
