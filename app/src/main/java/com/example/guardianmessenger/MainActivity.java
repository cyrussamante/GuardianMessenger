package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guardianmessenger.utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button messageBtn, LogoutBtn;

    @Override
    protected void onStart() {
        super.onStart();
        if(!FirebaseUtils.isLoggedIn()){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        messageBtn = findViewById(R.id.message_button);
        LogoutBtn = findViewById(R.id.logout_button);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
//        Messages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,HomeScreen.class));
//            }
//        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(i);
            }
        });

    }
}