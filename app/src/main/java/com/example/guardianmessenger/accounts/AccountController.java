package com.example.guardianmessenger.accounts;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.EmployeeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

/**
 * Feature: Controller for Managing and Updating Accounts
 */
public class AccountController {

    private String empName, position, department;
    private int salary, empAge;
    private EmployeeModel currentUser;
    private final EditText nameField, positionField, departmentField, salaryField, ageField;

    /**
     * Constructor for the account controller.
     * Initializes the information for the UI to display.
     *
     * @param name: input box for name attribute
     * @param pos: input box for position attribute
     * @param dept: input box for department attribute
     * @param sal: input box for salary attribute
     * @param age: input box for age attribute
     */
    public AccountController(EditText name, EditText pos, EditText dept, EditText sal, EditText age) {
        this.nameField = name;
        this.positionField = pos;
        this.departmentField = dept;
        this.salaryField = sal;
        this.ageField = age;

        // get current user details and display them on text fields
        DocumentReference user = FirebaseUtils.getUserDetails();
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        currentUser = document.toObject(EmployeeModel.class);
                        empName = currentUser.getName();
                        position = currentUser.getPosition();
                        department = currentUser.getDepartment();
                        salary = currentUser.getSalary();
                        empAge = currentUser.getAge();

                        nameField.setText(empName != null? empName : null);//
                        positionField.setText(position != null? position : null);//
                        departmentField.setText(department != null? department : null);//
                        salaryField.setText(String.valueOf(salary));
                        ageField.setText(String.valueOf(empAge));
                    }
                }
            }
        });

    }

    /**
     * Updates the employee attributes from the data within all text fields.
     * @return the employee model with updated values
     */
    public EmployeeModel updateValues() {
        // get values from fields
        empName = String.valueOf(nameField.getText());
        position = String.valueOf(positionField.getText());
        department = String.valueOf(departmentField.getText());

        // update user models
        if (!Objects.equals(empName, "")) {
            currentUser.setName(empName);
        } else {
            currentUser.setName(null);
        }

        if (!Objects.equals(position, "")) {
            currentUser.setPosition(position);
        } else {
            currentUser.setPosition(null);
        }

        if (!Objects.equals(department, "")) {
            currentUser.setDepartment(department);
        } else {
            currentUser.setDepartment(null);
        }

        // check if number format is correct for salary and age
        try {
            salary = Integer.parseInt(String.valueOf(salaryField.getText()));
            currentUser.setSalary(salary);
            empAge = Integer.parseInt(String.valueOf(ageField.getText()));
            currentUser.setAge(empAge);
        } catch (NumberFormatException e) {
            Log.e("MANAGE ACCOUNT", "Invalid number format.");
        }

        return currentUser;
    }
}
