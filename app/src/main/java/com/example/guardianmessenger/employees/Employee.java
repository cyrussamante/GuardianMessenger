package com.example.guardianmessenger.employees;

public class Employee {
    private int id;
    private EmployeeInfo info;
    private String username;
    private String password;

    public Employee(int id, EmployeeInfo info, String username, String password) {
        this.id = id;
        this.info = info;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public EmployeeInfo getInfo() {
        return info;
    }

    public void updateInfo(EmployeeInfo info) {
        this.info = info;
    }
}
