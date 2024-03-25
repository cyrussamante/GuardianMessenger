package com.example.guardianmessenger.accounts;

public class Employee {
    private final int ID;
    private EmployeeInfo info;
    private String username;
    private String password;

    public Employee(int ID, EmployeeInfo info, String username, String password) {
        this.ID = ID;
        this.info = info;
        this.username = username;
        this.password = password;
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
