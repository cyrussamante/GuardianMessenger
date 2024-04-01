package com.example.guardianmessenger;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;



public class ManageAccountActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button updateButton,  deleteButton;
    private EditText nameField, positionField, departmentField, salaryField, ageField;
    private String name, position, department;
    private int salary, age;
    private UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_account);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // button initialization
        backButton = findViewById(R.id.back_button);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // fields initialization
        nameField = findViewById(R.id.nameInput);
        positionField = findViewById(R.id.positionInput);
        departmentField = findViewById(R.id.departmentInput);
        salaryField = findViewById(R.id.salaryInput);
        ageField = findViewById(R.id.ageInput);

        DocumentReference user = FirebaseUtils.getUserDetails();
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        currentUser = document.toObject(UserModel.class);
                        name = currentUser.getName();
                        position = currentUser.getPosition();
                        department = currentUser.getDepartment();
                        salary = currentUser.getSalary();
                        age = currentUser.getAge();

                        nameField.setText(name != null? name : null);//
                        positionField.setText(position != null? position : null);//
                        departmentField.setText(department != null? department : null);//
                        salaryField.setText(String.valueOf(salary));
                        ageField.setText(String.valueOf(age));

                    }
                }
            }
        });

        // back button listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageAccountActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // update button listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                FirebaseUtils.getUserDetails().set(currentUser);
                Toast.makeText(ManageAccountActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
            }
        });


        // delete button listener
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

    // update all values within activity
    private void updateValues() {

        // get values from fields
        name = String.valueOf(nameField.getText());
        position = String.valueOf(positionField.getText());
        department = String.valueOf(departmentField.getText());

        // update user models
        if (!Objects.equals(name, "")) {
            currentUser.setName(name);
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
            age = Integer.parseInt(String.valueOf(ageField.getText()));
            currentUser.setSalary(age);
        } catch (NumberFormatException e) {
            Log.e("MANAGE ACCOUNT", "Invalid number format.");
        }
    }
}