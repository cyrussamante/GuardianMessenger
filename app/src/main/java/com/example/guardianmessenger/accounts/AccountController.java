package com.example.guardianmessenger.accounts;

import com.example.guardianmessenger.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


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

    public void createAccount(FirebaseFirestore db, int employeeId, EmployeeInfo info) {
        db.collection("employees").document(String.valueOf(employeeId)).set(info)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("CreateAccount", "Successfully created account");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("CreateAccount", "Error creating account", e);
                }
            });
    }
}
