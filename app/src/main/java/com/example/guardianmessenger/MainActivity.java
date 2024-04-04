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
import com.example.guardianmessenger.outreach.OutreachActivity;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.SessionController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * 3A04 - Deliverable 4 (T03 - Group 9)
 * Guardian Messenger
 * Members: Cyruss Allen Amante, Ali Virk
 *          Andrew McLaren, Savinay Chhabra, Kate Min
 *
 * The main UI of the application once logged in.
 */

public class MainActivity extends AppCompatActivity {

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
        Button messageBtn = findViewById(R.id.message_button);
        Button reqChatLogBtn = findViewById(R.id.request_chat_logs);
        Button manageBtn = findViewById(R.id.manage_button);
        Button massBtn = findViewById(R.id.mass_message_button);
        massMessage = findViewById(R.id.massMsgText);
        Button logoutBtn = findViewById(R.id.logout_button);
        Button outreachBtn = findViewById(R.id.request_outreach_button);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // message button listener
        SessionController.redirectButton(messageBtn, MainActivity.this, MessageActivity.class);

        // manage account button listener
        SessionController.redirectButton(manageBtn, MainActivity.this, ManageAccountActivity.class);

        // request chat logs button listener
        SessionController.redirectButton(reqChatLogBtn, MainActivity.this, RequestChatLogActivity.class);

        // mass message button listener
        SessionController.redirectButton(massBtn, MainActivity.this, MassMessageActivity.class);

        // outreach listener
        SessionController.redirectButton(outreachBtn, MainActivity.this, OutreachActivity.class);

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