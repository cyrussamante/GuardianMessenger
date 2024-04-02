package com.example.guardianmessenger;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class OutreachActivity2 extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText employee;
    Button submitRequest;
    ImageButton goBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//
//        setContentView(R.layout.outreach_screen);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        //Fix these lines
//        employee = findViewById(R.id.employee_id);
//        submitRequest = findViewById(R.id.submit_button);
//        goBack = findViewById(R.id.back_button_outreach);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        submitRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String employeeId = employee.getText().toString();
//                String currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//                OutreachController controller = new OutreachController();
//                boolean success = controller.requestOutreach(currentUser, employeeId);
//                if (success) {
//                    Toast.makeText(OutreachActivity.this, "Outreach request Approved", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(OutreachActivity.this, "Outreach request Denied", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//         back button listener
//        goBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(OutreachActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });
    }
}
