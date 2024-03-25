package com.example.guardianmessenger.accounts;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountController {

    private EmployeeDB employeeDB;

    public AccountController(EmployeeDB db) {
        this.employeeDB = db;
    }

    /***
     * Get copy of an employee's info
     * @return EmployeeInfo
     */
    public EmployeeInfo getEmployeeInfo(int employeeID) {
        Employee employee = employeeDB.getEmployee(employeeID);
        return new EmployeeInfo(employee.getInfo());
    }


    /***
     * Update an employee's info
     */
    public void updateEmployeeInfo(int employeeId, EmployeeInfo newEmployeeInfo) {
        //TODO: verify that the user has permission to update the account
        Employee employee = employeeDB.getEmployee(employeeId);
        employee.updateInfo(newEmployeeInfo);
    }
    /***
     * Delete an account
     */
    public void deleteAccount(int employeeId) {
        //TODO: verify that the user has permission to delete the account
        employeeDB.removeEmployee(employeeId);
    }


}
