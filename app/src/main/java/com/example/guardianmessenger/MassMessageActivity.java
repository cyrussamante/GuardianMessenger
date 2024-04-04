package com.example.guardianmessenger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.messaging.MassMessageController;
import com.example.guardianmessenger.utils.SessionController;

/**
 * Feature: Mass Message UI
 */
public class MassMessageActivity extends AppCompatActivity {

    private EditText massMessageField;

    /**
     * Creates the mass message activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mass_message);

        Button removeButton = findViewById(R.id.removeButton);
        Button submitButton = findViewById(R.id.submitButton);
        ImageButton backButton = findViewById(R.id.back_button);
        massMessageField = findViewById(R.id.massMessageField);

        // back button listener
        SessionController.redirectButton(backButton, MassMessageActivity.this, MainActivity.class);

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