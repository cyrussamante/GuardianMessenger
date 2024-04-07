package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.SessionController;
import com.example.guardianmessenger.utils.EmployeeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity for Registration Page
 */
public class RegistrationPage extends AppCompatActivity {
    private Button btnCreate;
    private ImageButton backButton;
    private EditText registrationName, registrationEmail, registrationPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.register_screen);
        mAuth = FirebaseAuth.getInstance();
        registrationName= findViewById(R.id.name);
        registrationEmail = findViewById(R.id.email_id);

        registrationPassword = findViewById(R.id.password);
        backButton = findViewById(R.id.back_button);
        btnCreate = findViewById(R.id.registerButton);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, password;
                name = registrationName.getText().toString();
                email = registrationEmail.getText().toString();
                password = registrationPassword.getText().toString();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)|| TextUtils.isEmpty(name)){
                    Toast.makeText(RegistrationPage.this, "At least one field is empty", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                registerUser(mAuth.getCurrentUser().getUid());
                                Toast.makeText(RegistrationPage.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegistrationPage.this, "Registration Failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // back button listener
        SessionController.redirectButton(backButton, RegistrationPage.this, LoginPage.class);
    }

    void registerUser(String userID){
            String userEmail = registrationEmail.getText().toString();
            String name = registrationName.getText().toString();
            if (userEmail.isEmpty() ||userEmail.length()<3){
                registrationEmail.setError("Too short");
                return;
            }
            EmployeeModel employeeModel = new EmployeeModel(Timestamp.now(),userID, userEmail);
            employeeModel.setName(name);
            FirebaseUtils.getUserDetails().set(employeeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistrationPage.this, "Added User Details", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationPage.this, HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            });
    }
}