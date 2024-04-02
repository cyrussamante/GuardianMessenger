package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.messaging.MassMessageController;

public class MassMessageActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button submitButton, removeButton;
    private EditText massMessageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mass_message);

        removeButton = findViewById(R.id.removeButton);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.back_button);
        massMessageField = findViewById(R.id.massMessageField);

        // back button listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MassMessageActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // submit button listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MassMessageController.setMassMessage(String.valueOf(massMessageField.getText()));
                Toast.makeText(MassMessageActivity.this, "Mass Message Sent", Toast.LENGTH_SHORT).show();
            }
        });

        // remove button listener
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MassMessageController.setMassMessage("");
                Toast.makeText(MassMessageActivity.this, "Mass Message Removed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}