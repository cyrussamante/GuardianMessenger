package com.example.guardianmessenger;

import static com.example.guardianmessenger.R.id.*;
import static com.example.guardianmessenger.accounts.EmployeeDB.getDB;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.accounts.AccountController;
import com.example.guardianmessenger.accounts.Employee;
import com.example.guardianmessenger.accounts.EmployeeDB;
import com.example.guardianmessenger.outreach.OutreachController;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class OutreachActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText employee;
    Button submitRequest;

    EmployeeDB employeeDB;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outreach_screen);

        mAuth = FirebaseAuth.getInstance();

        employeeDB = getDB();
        //Fix these lines
        employee = findViewById(R.id.employee_id);
        submitRequest = findViewById(R.id.button_button);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeId = employee.getText().toString();
                Employee employee = employeeDB.getEmployee(employeeId);
                Employee currentUser = employeeDB.getEmployee(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                OutreachController controller = new OutreachController();
                boolean success = controller.requestOutreach(currentUser, employee);
                if (success) {
                    Toast.makeText(OutreachActivity.this, "Outreach request Approved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OutreachActivity.this, "Outreach request Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
