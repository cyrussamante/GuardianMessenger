package com.example.guardianmessenger;

import static com.example.guardianmessenger.outreach.OutreachController.requestOutreach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class OutreachActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText employee;
    Button submitRequest;
    ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outreach_screen);

        mAuth = FirebaseAuth.getInstance();

        //Fix these lines
        employee = findViewById(R.id.employee_email);
        submitRequest = findViewById(R.id.submit_button);
        goBack = findViewById(R.id.back_button_outreach);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeEmail = String.valueOf(employee.getText());
                String currentUser = mAuth.getCurrentUser().getUid();
                boolean success = requestOutreach(currentUser, employeeEmail);
                if (success) {
                    Toast.makeText(OutreachActivity.this, "Outreach request Approved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OutreachActivity.this, "Outreach request Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //back button listener
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OutreachActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}