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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;


public class ManageAccountActivity extends AppCompatActivity {

    ImageButton backButton;
    Button updateButton;
    EditText nameField, positionField, departmentField, salaryField;
    String name, position, department, salary, age;

    HashMap<String, EditText> fieldMappings = new HashMap<>();
    HashMap<String, String> valueMappings = new HashMap<>();

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

        DocumentReference user = FirebaseUtils.getUserDetails();
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name = document.getString("name");
                        nameField.setText(name != null? name : "Name");
                        valueMappings.put("name", name);

                        position = document.getString("position");
                        positionField.setText(position != null? position : "Position");
                        valueMappings.put("position", position);

                        department = document.getString("department");
                        departmentField.setText(department != null? department : "Department");
                        valueMappings.put("department", department);

                        salary = String.valueOf(document.getLong("salary"));
                        salaryField.setText(!salary.equals("0") ? department : "Salary");
                        valueMappings.put("salary", salary);


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
                Intent i = new Intent(ManageAccountActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateValues() {
        for (String v : valueMappings.keySet()) {
            fieldMappings.get(v);
        }
    }




}