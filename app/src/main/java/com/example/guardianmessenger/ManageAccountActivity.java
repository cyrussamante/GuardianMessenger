package com.example.guardianmessenger;

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

import java.util.HashMap;


public class ManageAccountActivity extends AppCompatActivity {

    ImageButton backButton;
    Button updateButton;
    EditText nameField, positionField, departmentField, salaryField, ageField;
    String name, position, department;
    int salary, age;
    UserModel currentUser;

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

        backButton = findViewById(R.id.back_button);
        updateButton = findViewById(R.id.updateButton);

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

                        nameField.setText(name != null? name : "Name");//
                        positionField.setText(position != null? position : "Position");//
                        departmentField.setText(department != null? department : "Department");//
                        salaryField.setText(salary != 0 ? String.valueOf(salary): "Salary");
                        ageField.setText(age != 0 ? String.valueOf(age) : "Age");


                    }
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageAccountActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateValues();
                FirebaseUtils.getUserDetails().set(currentUser);
                Toast.makeText(ManageAccountActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateValues() {
        name = String.valueOf(nameField.getText());
        position = String.valueOf(positionField.getText());
        department = String.valueOf(departmentField.getText());
        salary = Integer.parseInt(String.valueOf(salaryField.getText()));
        age = Integer.parseInt(String.valueOf(ageField.getText()));

        if (name != null) {currentUser.setName(name);}
        if (position != null) {currentUser.setPosition(position);}
        if (department != null) {currentUser.setDepartment(department);}
        if (salary != 0) {currentUser.setSalary(salary);}
        if (age != 0) {currentUser.setSalary(age);}

    }



}