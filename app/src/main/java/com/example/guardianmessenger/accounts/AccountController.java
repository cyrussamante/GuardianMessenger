package com.example.guardianmessenger.accounts;

import com.example.guardianmessenger.employees.*;
import com.google.firebase.auth.*;
import com.google.firebase.*;


public class AccountController {

    private FirebaseAuth mAuth;
    public AccountController() {
        // Empty constructor
    }

    public EmployeeInfo getUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String name = user.getDisplayName();
        String email = user.getEmail();
        return new EmployeeInfo(name, email);
    }

    public EmployeeInfo getEmployeeInfo(Employee employee) {
        //TODO: Get employee info from the database
        return new EmployeeInfo(null, null);
    }

    public void updateUserInfo(EmployeeInfo employeeInfo) {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.updateEmail(employeeInfo.email);
        user.updateProfile(new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                .setDisplayName(employeeInfo.name)
                .build());
    }

    public void updateEmployeeInfo(EmployeeInfo employeeInfo) {
        //TODO: Update employee info in the database
    }

    public void deleteUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.delete();
    }

    public void deleteAccount(Employee employee) {
        //TODO: Delete account from the database
    }


}
