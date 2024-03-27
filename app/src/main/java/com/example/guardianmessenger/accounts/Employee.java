package com.example.guardianmessenger.accounts;

public class Employee {
    private final String ID;
    private EmployeeInfo info;


    public Employee(String ID, EmployeeInfo info) {
        this.ID = ID;
        this.info = info;
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
