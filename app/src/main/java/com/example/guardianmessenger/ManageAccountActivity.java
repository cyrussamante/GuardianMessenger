package com.example.guardianmessenger;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.accounts.AccountController;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.SessionController;

/**
 * Feature: Manage Account UI
 */
public class ManageAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_account);

        // button initialization
        ImageButton backButton = findViewById(R.id.back_button);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        // fields initialization
        EditText nameField = findViewById(R.id.nameInput);
        EditText positionField = findViewById(R.id.positionInput);
        EditText departmentField = findViewById(R.id.departmentInput);
        EditText salaryField = findViewById(R.id.salaryInput);
        EditText ageField = findViewById(R.id.ageInput);
        AccountController accountController = new AccountController(nameField, positionField, departmentField, salaryField, ageField);

        // back button listener
        SessionController.redirectButton(backButton, ManageAccountActivity.this, MainActivity.class);


        // update button listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtils.getUserDetails().set(accountController.updateValues());
                Toast.makeText(ManageAccountActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
            }
        });


        // delete button listener, ensures confirmation of account deletion
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageAccountActivity.this);
                builder.setTitle("Account Deletion Confirmation");
                builder.setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUtils.deleteUser(ManageAccountActivity.this);
                                startActivity(new Intent(ManageAccountActivity.this, LoginActivity.class));
                            }
                        })
                        .setNegativeButton("No", null).show();

            }
        });
    }
}