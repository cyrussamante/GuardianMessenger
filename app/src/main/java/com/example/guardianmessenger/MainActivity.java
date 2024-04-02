package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.messaging.MassMessageController;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    private Button messageBtn, LogoutBtn, manageBtn, massBtn, reqChatLogBtn;
    private LinearLayout massMessageWindow;
    private TextView massMessage;

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

        massMessageWindow = findViewById(R.id.massMsgWindow);
        messageBtn = findViewById(R.id.message_button);
        reqChatLogBtn = findViewById(R.id.request_chat_logs);
        manageBtn = findViewById(R.id.manage_button);
        massBtn = findViewById(R.id.mass_message_button);
        massMessage = findViewById(R.id.massMsgText);
        LogoutBtn = findViewById(R.id.logout_button);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // message button listener
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(i);
            }
        });

        // manage account button listener
        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ManageAccountActivity.class);
                startActivity(i);
            }
        });

        // request chat logs button listener
        reqChatLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RequestChatLogActivity.class);
                startActivity(i);
            }
        });

        // mass message button listener
        massBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MassMessage.class);
                startActivity(i);
            }
        });

        // mass message listener
        MassMessageController.messageDocument.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("Mass Message Activity", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    if (!snapshot.getString("msg").isEmpty()){
                        massMessage.setText(snapshot.getString("msg"));
                        massMessageWindow.setVisibility(View.VISIBLE);
                    } else {
                        massMessageWindow.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("Mass Message Activity", "Current data: null");
                }
            }
        });

    }
}