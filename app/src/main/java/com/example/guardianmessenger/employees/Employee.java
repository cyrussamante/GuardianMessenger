package com.example.guardianmessenger.employees;

public class Employee {
    private final String ID;
    private EmployeeInfo info;
    private String username;
    private String password;

    public Employee(String ID, EmployeeInfo info, String username, String password) {
        this.ID = ID;
        this.info = info;
        this.username = username;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public EmployeeInfo getInfo() {
        return info;
    }

    public void updateInfo(EmployeeInfo info) {
        this.info = info;
    }
}
