package com.example.guardianmessenger.accounts;

public class Employee {
    private final int ID;
    private EmployeeInfo info;


    public Employee(int ID, EmployeeInfo info) {
        this.ID = ID;
        this.info = info;
    }

    public int getID() {
        return ID;
    }

    public EmployeeInfo getInfo() {
        return info;
    }

    public void updateInfo(EmployeeInfo info) {
        this.info = info;
    }
}
